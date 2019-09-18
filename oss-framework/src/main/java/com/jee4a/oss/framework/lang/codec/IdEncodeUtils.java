package com.jee4a.oss.framework.lang.codec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.jee4a.oss.framework.exceptions.BusinessException;

/**
 * 格式判断工具
 *
 */
public class IdEncodeUtils {

    public static String KeySalt = "K_sfxh3l131311asdfds_lDSDFe";
    //public static String OpenIdSalt = "skifl398";
    
    public static byte[] sha128(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(src.getBytes());
            return Arrays.copyOf(md.digest(), 16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    // + to _ , / to -
    private static String base64ToOpenId(String base64) {
        return base64.replace('+', '_').replace('/', '-');
    }
    
    private static String openIdToBase64(String openId) {
        return openId.replace('_', '+').replace('-', '/');
    }
    
    /**
     * 
     * @param clientId
     * @param id
     * @param salt 必须为字母
     * @return
     */
    public static String id2OpenId(String clientId, int id, String salt) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException("clientId is null");
        }
        if (StringUtils.isEmpty(salt)) {
            throw new BusinessException("salt is null");
        }
        byte[] aesKey = sha128(KeySalt /* + clientId*/);
        String saltUserId = salt + String.format("%09d", id);
        return base64ToOpenId(CryptoUtils.aesEncryptToBase64(saltUserId, aesKey));
    }
    
    public static Integer openId2Id(String clientId, String openId, String salt) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException("缺少 client id");
        }
        if (StringUtils.isEmpty(openId)) {
            throw new BusinessException("缺少 open id");
        }
        byte[] aesKey = sha128(KeySalt/* + clientId*/);
        try {
            String saltUserId = CryptoUtils.aesDecryptFromBase64(openIdToBase64(openId), aesKey);
            String[] split = StringUtils.split(saltUserId, salt);
            if (split.length != 2) {
                throw new BusinessException("没有发现salt，decrypt string：" + saltUserId + ", salt:" + salt);
            }
            return Integer.parseInt(split[1]);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(IdEncodeUtils.openId2Id("abce", "3_TDgtt7XHxiMAQ9CLx1ag==", "u"));
    }
}
