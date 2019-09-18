package com.jee4a.oss.auth.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.code.kaptcha.Producer;
import com.jee4a.oss.auth.common.config.ConfigValues;
import com.jee4a.oss.auth.common.enums.SysDeletedEnum;
import com.jee4a.oss.auth.common.enums.SysUserStateEnum;
import com.jee4a.oss.auth.mapper.SysUserLoginLogMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.model.CacheKeys;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.auth.model.sys.SysUserLoginLog;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.CommonConstants;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.custom.LoginInfoVO;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;
import com.jee4a.oss.framework.lang.Assert;
import com.jee4a.oss.framework.lang.DateFormatUtils;
import com.jee4a.oss.framework.lang.PasswordUtils;

/**
 * @desc 权限校验类 
 * @author 398222836@qq.com
 * @date 2019年7月23日
 */
@Service
public class LoginService extends BaseService {
	
	protected  static final PathMatcher pathMatcher = new AntPathMatcher() ;
	
	@Resource
	private ConfigValues configValues;
	
	@Resource
	private Producer producer;
	
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysUserLoginLogMapper sysUserLoginLogMapper;

	@Resource
	protected RedisUtils redisUtils;
	
	
	public void createCaptcha(String sessionId,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到shiro session
		redisUtils.setex(CacheKeys.KEY_KAPTCHA_SESSION_KEY.getKeyPrefix(sessionId), CacheKeys.KEY_KAPTCHA_SESSION_KEY.getExpire(), text);
		try {
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			logger.error("生成验证码错误：" + e.getMessage(), e);
		}
	}
	
	public Result<?> login(String captcha,String sessionId, String userName, String password, String ip, String userAgent,HttpServletResponse response) {
		Result<?> result = new Result<>();
		SysUser user = null;
		try {
			
			Assert.isNotBlank(userName, -1003, "userName 不能为空");
			Assert.isNotBlank(password, -1004, "password 不能为空");
			// 按用户名查询用户
			user = sysUserMapper.selectByUserName(userName);
			if(user == null) {
				// 按手机号查询用户
				user = sysUserMapper.selectByMobile(userName);
			}
			Assert.notNull(user, -1005, "账号或密码不正确");
			
			Assert.isTrue(user.getState().intValue()==SysUserStateEnum.DISABLED.getK(), -1006, "账号或密码不正确");
			
			Assert.isTrue(user.getIsDeleted().intValue()==SysDeletedEnum.DELETED.getK(), -1007, "账号已删除，请联系管理员");
	        
			Date lastUpdatePwd   = new Date();
			boolean isUpdatePwd = DateFormatUtils.daysBeetween(new Timestamp( lastUpdatePwd.getTime()), new Timestamp(new Date().getTime())) >= 90;
			Assert.isTrue(isUpdatePwd,-1009, "距离上次修改密码超过90天，请修改密码再登录");
			
			if (configValues.isCaptchaIsOpen()) {
				Assert.isNotBlank(captcha, -1004, "验证码不能为空");
				Assert.isNotBlank(sessionId, -1004, "sessionId 不能为空");
				String kaptcha = redisUtils.get(CacheKeys.KEY_KAPTCHA_SESSION_KEY.getKeyPrefix(sessionId));
				Assert.isTrue(captcha.equalsIgnoreCase(kaptcha),-1009, "验证码不正确");
			}
			
			Assert.isTrue(PasswordUtils.getPassword(password, user.getSalt()).equals(user.getUserPwd()),-403, "用户名或密码不正确");
			Result<String> tokenResult = createToken(user.getId(), user.getSalt()) ;
			if(tokenResult.isFault()) {
				return result ;
			}
			LoginInfoVO loginInfoVO = new LoginInfoVO() ;
			loginInfoVO.setId(user.getId());
			loginInfoVO.setNickName(user.getUserName());
			loginInfoVO.setIp(ip);
			loginInfoVO.setLoginTime(new Date());
			loginInfoVO.setExpireTime(DateFormatUtils.addSec(loginInfoVO.getLoginTime(), 1*60*60));
			loginInfoVO.setClientInfo(userAgent);
			String key = CacheKeys.KEY_USER_LOGIN_INFO.getKeyPrefix(user.getId());
			redisUtils.hset(key, tokenResult.get(), loginInfoVO.toJson());
			
			response.setHeader(CommonConstants.AUTH_NAME, tokenResult.get());
			result.setSuccess();
			logLogin(userName, ip, userAgent, result.getCode(), user == null ? null : user.getId());
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	private void logLogin(String userName, String ip, String userAgent, Integer loginStatus, Integer userId) {
		try {
			SysUserLoginLog log = new SysUserLoginLog();
			log.setUserName(userName);
			log.setLoginStatus(loginStatus);
			log.setLoginTime(new Date());
			log.setIp(ip);
			log.setClientInfo(userAgent);
			if (null != userId) {
				log.setUserId(userId);
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					sysUserLoginLogMapper.insertSelective(log);
				}
			}).start();
		} catch (Exception e) {
		}
	}
	 
	public Result<String> createToken(Integer userId,String salt){
		Result<String> result = new Result<>() ;
		Algorithm algorithm = Algorithm.HMAC256(salt);
		Date date = new Date();
		String token =  JWT.create()
                .withClaim(CommonConstants.JWT_PAYLOAD_CLAIMS, userId)
                .withIssuedAt(date)
                .withExpiresAt(DateFormatUtils.addSec(date,24*60*60))
                .sign(algorithm);
		result.put(token);
		result.setSuccess();
		return result ;
	}
	
	public Result<?> logout(String token){
		Result<?> result = new Result<>() ;
		try {
			String arg[] = token.split("\\.");
			Assert.isTrue(arg.length == 3,-1009, "无效的token");
			DecodedJWT jwt = JWT.decode(token);
			Integer userId = jwt.getClaim(CommonConstants.JWT_PAYLOAD_CLAIMS).asInt() ;
			String key = CacheKeys.KEY_USER_LOGIN_INFO.getKeyPrefix(userId);
			redisUtils.hdel(key, token);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return result ;
	}
	
	
	public static void main(String[] args) {
		String salt = "12345";//JwtUtils.generateSalt();
		 Date date = new Date();
         Algorithm algorithm = Algorithm.HMAC256(salt);
         // 附带username信息
         String token =  JWT.create()
                 .withClaim("username", "tangpeng")
                 .withExpiresAt(date)
                 .withIssuedAt(new Date())
                 .sign(algorithm);
         System.out.println("token"+ token );
		 DecodedJWT jwt = JWT.decode(token);
         System.out.println(jwt.getClaim("username").asString());
	}
}
