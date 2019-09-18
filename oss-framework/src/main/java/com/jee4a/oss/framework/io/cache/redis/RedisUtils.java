package com.jee4a.oss.framework.io.cache.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.jee4a.oss.framework.lang.JsonUtils;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.util.Hashing;
import redis.clients.util.SafeEncoder;

/**
 * redis常用命令简单封装sharding版本
 * 
 */
@Configuration
public class RedisUtils {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(RedisUtils.class);

	private String uri;
	
	private ShardedJedisPool pool;

	private JedisPoolConfig jedisPoolConfig ;

	private static RedisUtils instance;

	static {
		log.info("redis初始化开始。。。。。");
	}

	@PostConstruct
	public void init() {
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = uri.split(",");
		for (String str : addressArr) {
			String[] split = str.split(":");
			String ip = split[0];
			String port = split[1];
			JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, Integer.parseInt(port));
			if (split.length > 3) {
				jedisShardInfo.setPassword(str.substring(str.indexOf(":", (ip + ":" + port + ":").length())));
			} else if (split.length == 3) {
				jedisShardInfo.setPassword(split[2]);
			}
			list.add(jedisShardInfo);
			log.info("redis服务端 ip:" + ip + "  port:" + port);
		}
		pool = new ShardedJedisPool(jedisPoolConfig, list);
		Collection<Jedis> allShards = pool.getResource().getAllShards();
		for (Jedis jedis : allShards) {
			log.info("redis-pingtest    ip:" + jedis.getClient().getHost() + "  port:" + jedis.getClient().getPort());
			jedis.ping();
		}
		instance = this;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
	
	public RedisUtils() {
		instance = this;
	}

	public static RedisUtils getInstance() {
		return instance;
	}

	/**
	 * 关闭 Redis
	 */
	public void destory() {
		pool.destroy();
	}

	/**
	 * 获取 ShardedJedis
	 * 
	 * @return
	 */
	public ShardedJedis getResource() {
		return pool.getResource();
	}

	/**
	 * 回收 ShardedJedis
	 * 
	 * @param shardedJedis
	 */
	public void returnResource(ShardedJedis shardedJedis) {
		if (shardedJedis == null) {
			return;
		}
		try {
			pool.returnResource(shardedJedis);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * 销毁 ShardedJedis
	 * 
	 * @param shardedJedis
	 */
	public void returnBrokenResource(ShardedJedis shardedJedis) {
		if (shardedJedis == null) {
			return;
		}
		try {
			pool.returnBrokenResource(shardedJedis);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * list尾部添加元素
	 * 
	 * @see link http://redis.cn/commands/rpush.html
	 * @return list长度
	 */
	public long rpush(String key, String... string) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long ret = shardedJedis.rpush(key, string);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public long lpush(String key, String... string) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long ret = shardedJedis.lpush(key, string);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * set
	 * 
	 * @see link http://redis.cn/commands/sadd.html
	 * @return
	 */
	public long sadd(String key, String... members) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long res = shardedJedis.sadd(key, members);
			return res;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 弹出头部元素
	 * 
	 * @see link http://redis.cn/commands/lpop.html
	 * @return string 头部元素
	 */
	public String lpop(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.lpop(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public List<String> blpop(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			List<String> ret = shardedJedis.blpop(key);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public List<String> brpop(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			List<String> ret = shardedJedis.brpop(key);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public <T> T lpop(String key, Class<T> clazz) {
		return JsonUtils.fromJson(lpop(key), clazz);
	}

	/**
	 * 弹出头部元素
	 * 
	 * @see link http://redis.cn/commands/rpop.html
	 * @return
	 */
	public String rpop(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			String ret = shardedJedis.rpop(key);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public <T> T rpop(String key, Class<T> clazz) {
		return JsonUtils.fromJson(rpop(key), clazz);
	}

	/**
	 * 获取LIST长度
	 * 
	 * @see link http://redis.cn/commands/llen.html
	 * @return list长度
	 */
	public long llen(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long ret = shardedJedis.llen(key);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 删除LIST中的值
	 * 
	 * @see link http://redis.cn/commands/lrem.html
	 * @return list长度
	 */
	public long lrem(String key, long count, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long ret = shardedJedis.lrem(key, count, value);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 修剪
	 * 
	 * @see link http://redis.cn/commands/ltrim.html
	 * @return list长度
	 */
	public String ltrim(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			String ret = shardedJedis.ltrim(key, start, end);
			return ret;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 获取key这个List，从第几个元素到第几个元素 LRANGE key start
	 * 
	 * @param key
	 *            List别名
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下标
	 * @see link http://redis.cn/commands/lrange.html
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			List<String> list = shardedJedis.lrange(key, start, end);
			return list;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public <T> List<T> lrange(String key, long start, long end, Class<T> clazz) {
		List<String> lrange = lrange(key, start, end);
		List<T> returnList = new ArrayList();
		for (String string : lrange) {
			returnList.add(JsonUtils.fromJson(string, clazz));
		}
		return returnList;
	}

	/**
	 * 将哈希表key中的域field的值设为value。
	 * 
	 * @param key
	 *            哈希表别名
	 * @param field键
	 * @param value
	 *            值
	 * @see link http://redis.cn/commands/hset.html
	 */
	public void hset(String key, String field, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.hset(key, field, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public void hset(String key, String field, Object value) {
		hset(key, field, JsonUtils.toJson(value));
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @see link http://redis.cn/commands/set.html
	 */
	public void set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.set(key, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public void set(String key, Object value) {
		set(key, JsonUtils.toJson(value));
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/get.html
	 * @return
	 */
	public String get(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			String value = shardedJedis.get(key);
			return value;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public <T> T get(String key, Class<T> clazz) {
		return JsonUtils.fromJson(get(key), clazz);
	}

	public <T> T get(String key, Class<T> clazz, Class<?>... parameterClasses) {
		return JsonUtils.fromJson(get(key), clazz, parameterClasses);
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/get.html
	 * @return
	 */
	public long del(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			long value = shardedJedis.del(key);
			return value;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 * @see link http://redis.cn/commands/hmset.html
	 */
	public void hmset(String key, Map<String, String> map) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.hmset(key, map);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 * @see link http://redis.cn/commands/setex.html
	 */
	public void setex(String key, int seconds, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.setex(key, seconds, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public void setex(String key, int seconds, Object value) {
		setex(key, seconds, JsonUtils.toJson(value));
	}

	public Long setnx(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.setnx(key, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long setnx(String key, String value, int exprie) {
		ShardedJedisPipelineUtils pipelineUtils = buildPipelineUtils();
		pipelineUtils.setnx(key, value);
		pipelineUtils.expire(key, exprie);
		List<Object> syncAndReturnAll = pipelineUtils.syncAndReturnAll();
		return (Long) syncAndReturnAll.get(0);
	}

	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @see link http://redis.cn/commands/expire.html
	 * 
	 */
	public void expire(String key, int seconds) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param timestamp
	 *            unix时间戳
	 * @see link http://redis.cn/commands/expireat.html
	 * 
	 */
	public void expireAt(String key, long timestamp) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.expireAt(key, timestamp);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 获取key的有效时间
	 * 
	 * @param key
	 * @return seconds 生命时间 有效时间
	 * @see link http://redis.cn/commands/ttl.html
	 * 
	 */
	public Long ttl(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.ttl(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/exists.html
	 * @return
	 */
	public boolean exists(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			boolean bool = shardedJedis.exists(key);
			return bool;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回key值的类型 none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/type.html
	 * @return
	 */
	public String type(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			String type = shardedJedis.type(key);
			return type;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 * @see link http://redis.cn/commands/hget.html
	 */
	public String hget(String key, String field) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			String value = shardedJedis.hget(key, field);
			return value;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * sets size
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.scard(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * sets SRANDMEMBER
	 * 
	 * @param key
	 * @return
	 */
	public List<String> srandmember(String key, int count) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.srandmember(key, count);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回哈希表key中，所有值
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/smembers.html
	 * @return
	 */
	public Set<?> smembers(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			Set<?> set = shardedJedis.smembers(key);
			return set;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 移除集合中的member元素
	 * 
	 * @param key
	 *            List别名
	 * @param field
	 *            键
	 * @see link http://redis.cn/commands/srem.html
	 */
	public Long srem(String key, String... field) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.srem(key, field);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 判断member元素是否是集合key的成员。是（true），否则（false）
	 * 
	 * @param key
	 * @param field
	 * @see link http://redis.cn/commands/sismember.html
	 * @return
	 */
	public boolean sismember(String key, String field) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			boolean bool = shardedJedis.sismember(key, field);
			return bool;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public boolean sismember(String key, Object field) {
		return sismember(key, JsonUtils.toJson(field));
	}

	/**
	 * 如果key已经存在并且是一个字符串，将value追加到key原来的值之后
	 * 
	 * @param key
	 * @param value
	 * @see link http://redis.cn/commands/append.html
	 */
	public void append(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.append(key, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * -- key
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/decr.html
	 */
	public Long decr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.decr(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * key 减指定数值
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/decrBy.html
	 */
	public Long decrBy(String key, Integer integer) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.decrBy(key, integer);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/del.html
	 */
	public Long decrBy(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.del(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 这里的N是返回的string的长度。复杂度是由返回的字符串长度决定的，但是因为从一个已经存在的字符串创建一个子串是很容易的，所以对于较小的字符串，
	 * 可以认为是O(1)的复杂度。
	 * 
	 * @param key
	 * @see link http://redis.cn/commands/getrange.html
	 */
	public String decrBy(String key, int startOffset, int endOffset) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，就返回错误。
	 * 
	 * @param key
	 * @param value
	 * @see link http://redis.cn/commands/getSet.html
	 */
	public String getSet(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.getSet(key, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 从 key 指定的哈希集中移除指定的域。在哈希集中不存在的域将被忽略。如果 key 指定的哈希集不存在，它将被认为是一个空的哈希集，该命令将返回0。
	 * 
	 * 返回值 整数：返回从哈希集中成功移除的域的数量，不包括指出但不存在的那些域
	 * 
	 * 
	 * 
	 * @param key
	 * @param fields
	 * @see link http://redis.cn/commands/hdel.html
	 */
	public Long hdel(String key, String... fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hdel(key, fields);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回字段是否是 key 指定的哈希集中存在的字段。
	 * 
	 * 返回值 整数, 含义如下：
	 * 
	 * 1 哈希集中含有该字段。 0 哈希集中不含有该存在字段，或者key不存在。
	 * 
	 * 
	 * 
	 * 
	 * @param key
	 * @param fields
	 * @see link http://redis.cn/commands/hexists.html
	 */
	public Boolean hexists(String key, String fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hexists(key, fields);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Boolean hexists(String key, Object field) {
		return hexists(key, JsonUtils.toJson(field));
	}

	/**
	 * 增加 key 指定的哈希集中指定字段的数值。如果 key 不存在，会创建一个新的哈希集并与 key 关联。如果字段不存在，则字段的值在该操作执行前被设置为
	 * 0
	 * 
	 * HINCRBY 支持的值的范围限定在 64位 有符号整数
	 * 
	 * 返回值 整数：增值操作执行后的该字段的值。
	 * 
	 * 
	 * @param key
	 * @param fields
	 * @param value
	 * @see link http://redis.cn/commands/hincrBy.html
	 */
	public Long hincrBy(String key, String field, int value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hincrBy(key, field, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回 key 指定的哈希集中所有字段的名字。
	 * 
	 * 返回值 多个返回值：哈希集中的字段列表，当 key 指定的哈希集不存在时返回空列表。
	 * 
	 * @param key
	 * 
	 * @return 返回值为linkedhashset有序
	 * @see link http://redis.cn/commands/hkeys.html
	 */
	public Set<String> hkeys(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hkeys(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * key field 原子性incr value
	 * 
	 * 返回值incr后的值
	 * 
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * 
	 * @see link http://redis.cn/commands/hincrbyfloat.html
	 */
	public Double hincrbyfloat(String key, String field, double value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hincrByFloat(key, field, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 增量迭代一个集合元素 SCAN
	 * 命令是一个基于游标的迭代器。这意味着命令每次被调用都需要使用上一次这个调用返回的游标作为该次调用的游标参数，以此来延续之前的迭代过程 当 SCAN
	 * 命令的游标参数被设置为 0 时， 服务器将开始一次新的迭代， 而当服务器向用户返回值为 0 的游标时， 表示迭代已结束。<br>
	 * 量式迭代命令， 在进行完整遍历的情况下可以为用户带来以下保证 ： 从完整遍历开始直到完整遍历结束期间， 一直存在于数据集内的所有元素都会被完整遍历返回；
	 * 这意味着， 如果有一个元素， 它从遍历开始直到遍历结束期间都存在于被遍历的数据集当中， 那么 SCAN 命令总会在某次迭代中将这个元素返回给用户。
	 * 同样，如果一个元素在开始遍历之前被移出集合，并且在遍历开始直到遍历结束期间都没有再加入，那么在遍历返回的元素集中就不会出现该元素。
	 * 然而因为增量式命令仅仅使用游标来记录迭代状态， 所以这些命令带有以下缺点： 同一个元素可能会被返回多次。 处理重复元素的工作交由应用程序负责， 比如说，
	 * 可以考虑将迭代返回的元素仅仅用于可以安全地重复执行多次的操作上。 如果一个元素是在迭代过程中被添加到数据集的， 又或者是在迭代过程中从数据集中被删除的，
	 * 那么这个元素可能会被返回， 也可能不会。
	 * 
	 * 
	 * @param key
	 * @param cursor
	 *            游标
	 * @return
	 * 
	 * @see link http://redis.cn/commands/hscan.html
	 */
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hscan(key, cursor);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回 key 指定的哈希集包含的字段的数量。
	 * 
	 * 返回值 整数：哈希集中字段的数量，当 key 指定的哈希集不存在时返回 0
	 * 
	 * @param key
	 * 
	 * @see link http://redis.cn/commands/hlen.html
	 */
	public Long hlen(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hlen(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回 key 指定的哈希集中指定字段的值。
	 * 
	 * 对于哈希集中不存在的每个字段，返回 nil 值。因为不存在的keys被认为是一个空的哈希集，对一个不存在的 key 执行 HMGET 将返回一个只含有
	 * nil 值的列表
	 * 
	 * 返回值 多个返回值：含有给定字段及其值的列表，并保持与请求相同的顺序。
	 * 
	 * 
	 * 
	 * @param key
	 * @param fields
	 * @see link http://redis.cn/commands/hmget.html
	 */
	public List<String> hmget(String key, String... fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hmget(key, fields);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key
	 * 关联。如果字段已存在，该操作无效果。
	 * 
	 * 返回值 整数：含义如下
	 * 
	 * 1：如果字段是个新的字段，并成功赋值 0：如果哈希集中已存在该字段，没有操作被执行
	 * 
	 * 
	 * @param key
	 * 
	 * @see link http://redis.cn/commands/hsetnx.html
	 */
	public Long hsetnx(String key, String field, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hsetnx(key, field, value);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long hsetnx(String key, String field, Object value) {
		return hsetnx(key, field, JsonUtils.toJson(value));
	}

	/**
	 * 返回 key 指定的哈希集中所有字段的值。
	 * 
	 * 返回值 多个返回值：哈希集中的值的列表，当 key 指定的哈希集不存在时返回空列表。
	 * 
	 * 
	 * @param key
	 * @return 返回值为arraylist 有序
	 * @see link http://redis.cn/commands/hvals.html
	 */
	public List<String> hvals(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hvals(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * ++key
	 * 
	 * @param key
	 * 
	 * @see link http://redis.cn/commands/incr.html
	 */
	public Long incr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.incr(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * incr且值为val时设置过期时间
	 * 
	 * @param key
	 * @param expire
	 * @param val
	 * @return
	 */
	public Long incr(String key, int expire, int val) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			Long incr = shardedJedis.incr(key);
			if (incr == val) {
				shardedJedis.expire(key, expire);
			}
			return incr;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 参选，如果参选成功将为key续期,如果已有被选举人则判断是否为自己 为自己则续期
	 * 
	 * @param key
	 *            参选项目
	 * @param candidates
	 *            候选人
	 * @param timeOut
	 *            参选成功后的过期时间
	 * @return true 参选成功并续期成功 或 被选举人为自己并续期成功， false 参选失败或被选举人不为自己
	 */
	public boolean electioneer(String key, String candidates, int timeOut) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			int setnx = shardedJedis.setnx(key, candidates).intValue();
			boolean bool = false;
			if (setnx == 1) {
				shardedJedis.expire(key, timeOut);
				bool = true;
			} else {
				String electee = shardedJedis.get(key);
				if (electee != null && electee.equals(candidates)) {
					shardedJedis.expire(key, timeOut);
					bool = true;
				}
			}
			return bool;
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 防重提交，特定时间内只接受特定次数的重复提交 超过则返回冷却时间 1.初次写入 2.初次写入成功则创建过期时间
	 * 3.非初次写入则计数加1后判断是否超过最大值，超过则返回冷却时间
	 * 
	 * @param key
	 *            业务key
	 * @param maxSubmit
	 *            时间内最大提交次数，值不能小于1
	 * @param timeOut
	 *            计数时间周期，单位：毫秒
	 * @return 冷却时间 ，毫秒
	 */
	public long retry(String key, int maxSubmit, long timeOut) {
		if (maxSubmit < 1) {
			maxSubmit = 1;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			// 尝试首次创建
			Long nx = shardedJedis.setnx(key, "1");
			if (nx == 0) {
				Long incr = shardedJedis.incr(key);
				if (incr > maxSubmit) {
					Long pttl = shardedJedis.pttl(key);
					// 如果key存在但未设置过期时间，则取timeout
					if (pttl == -1) {
						return timeOut;
					} else if (pttl == -2) {
						return 0;
					}
					return pttl;
				}
			}
			// nx==1
			else {
				shardedJedis.pexpire(key, timeOut);
			}
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}

	/**
	 * @see RedisUtils#retry(String, int, long)
	 * @param key
	 * @param timeOut
	 * @return
	 */
	public long retry(String key, long timeOut) {
		return retry(key, 1, timeOut);
	}

	/**
	 * 
	 * 将key对应的数字加decrement。如果key不存在，操作之前，key就会被置为0。
	 * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。这个操作最多支持64位有符号的正型数字。
	 * 
	 * 查看命令INCR了解关于增减操作的额外信息。
	 * 
	 * 返回值 数字：增加之后的value值。
	 * 
	 * 
	 * @param key
	 * @param integer
	 * @see link http://redis.cn/commands/incrBy.html
	 */
	public Long incrBy(String key, int integer) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.incrBy(key, integer);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zadd(String key, double score, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zcard(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zcard(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zcount(String key, double min, double max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zcount(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zcount(String key, String min, String max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zcount(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Double zincrby(String key, double score, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zincrby(key, score, member);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zrank(String key, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrank(key, member);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zrem(String key, String... members) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrem(key, members);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zremrangeByRank(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zremrangeByScore(String key, double start, double end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zremrangeByScore(String key, String start, String end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrevrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrange(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public Long zrevrank(String key, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zrevrank(key, member);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public ScanResult<Tuple> zscan(String key, String cursor) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.zscan(key, cursor);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 订阅指定shardkey下的某些频道
	 * 
	 * @param shardkey
	 *            用于shard定向
	 * @param jedisPubSub
	 *            用于回调
	 * @param channels
	 *            频道名称
	 * @see link http://redis.cn/commands/subscribe.html
	 */
	public void subscribe(String shardkey, JedisPubSub jedisPubSub, String... channels) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.getShard(shardkey).subscribe(jedisPubSub, channels);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 向指定shardkey下的某频道发送消息
	 * 
	 * @param shardkey
	 *            用于shard定向
	 * @param channel
	 *            频道名称
	 * @param message
	 *            消息内容
	 * @see link http://redis.cn/commands/publish.html
	 */
	public void publish(String shardkey, String channel, String message) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			shardedJedis.getShard(shardkey).publish(channel, message);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 构建一个支持sharding的批处理工具类
	 * 
	 * @return
	 */
	public ShardedJedisPipelineUtils buildPipelineUtils() {
		return new ShardedJedisPipelineUtils(this.pool, this.getResource());
	}

	/**
	 * 根据shardKey获取jedis
	 * 
	 * @param shardKey
	 */
	public Jedis getJedisByShardKey(String shardKey) {
		try {
			return getResource().getShard(shardKey);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		}
	}

	/**
	 * 判断是否能提交
	 */
	public boolean canBeSubmit(String key, String value, int seconds) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return "OK".equalsIgnoreCase(shardedJedis.set(key, value, "NX", "EX", seconds));
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}

	/**
	 * 获取key在 shard分片中的index
	 * 
	 * @param uri
	 * @param key
	 * @return
	 */
	public static int getShardIndex(String uri, String key) {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String ips : uri.split(",")) {
			JedisShardInfo info = new JedisShardInfo(ips.split(":")[0], Integer.parseInt(ips.split(":")[1]));
			shards.add(info);
		}
		TreeMap<Long, JedisShardInfo> nodes = new TreeMap<Long, JedisShardInfo>();
		for (int i = 0; i != shards.size(); ++i) {
			final JedisShardInfo shardInfo = shards.get(i);
			if (shardInfo.getName() == null)
				for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
					nodes.put(Hashing.MURMUR_HASH.hash("SHARD-" + i + "-NODE-" + n), shardInfo);
				}
			else
				for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
					nodes.put(Hashing.MURMUR_HASH.hash(shardInfo.getName() + "*" + shardInfo.getWeight() + n),
							shardInfo);
				}
		}
		SortedMap<Long, JedisShardInfo> tail = nodes.tailMap(Hashing.MURMUR_HASH.hash(SafeEncoder.encode(key)));
		JedisShardInfo currJedisShardInfo = null;
		if (tail.size() == 0) {
			currJedisShardInfo = nodes.get(nodes.firstKey());
		} else {
			currJedisShardInfo = tail.get(tail.firstKey());
		}
		System.out.println(key + " saved " + currJedisShardInfo);
		for (int i = 0; i < shards.size(); i++) {
			if (shards.get(i).equals(currJedisShardInfo)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 返回哈希表key中，所有的域和值
	 * 
	 * @param key
	 * @param order
	 *            是否保持原始顺序
	 * @see link http://redis.cn/commands/hgetall.html
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.hgetAll(key);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}

	}

	/**
	 * 将指定的地理空间位置（纬度、经度、名称）添加到指定的key中
	 * 
	 * @param key
	 * @param memberCoordinateMap
	 * @see link http://redis.cn/commands/geoadd.html
	 * @return 添加到sorted set元素的数目，但不包括已更新score的元素。
	 */
	public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geoadd(key, memberCoordinateMap);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}

	}

	/**
	 * 将指定的地理空间位置（纬度、经度、名称）添加到指定的key中
	 * 
	 * @see link http://redis.cn/commands/geoadd.html
	 * @param key
	 * @param longitude
	 * @param latitude
	 * @param member
	 * @return 添加到sorted set元素的数目，但不包括已更新score的元素。
	 */
	public Long geoadd(String key, double longitude, double latitude, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geoadd(key, longitude, latitude, member);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}

	}

	/**
	 * 返回两个给定位置之间的距离。
	 * 
	 * @see link http://redis.cn/commands/geodist.html
	 * 
	 * @param key
	 * @param member1
	 *            位置1key
	 * @param member2
	 *            位置2key
	 * @return 计算出的距离会以双精度浮点数的形式被返回。 如果给定的位置元素不存在， 那么命令返回空值。
	 */
	public Double geodist(String key, String member1, String member2) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geodist(key, member1, member2);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}

	}

	/**
	 * 返回两个给定位置之间的距离。
	 * 
	 * @see link http://redis.cn/commands/geodist.html
	 * 
	 * @param key
	 * @param member1
	 *            位置1key
	 * @param member2
	 *            位置2key
	 * @param unit
	 *            m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺。
	 * @return 计算出的距离会以双精度浮点数的形式被返回。 如果给定的位置元素不存在， 那么命令返回空值。
	 */
	public Double geodist(String key, String member1, String member2, GeoUnit unit) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geodist(key, member1, member2, unit);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 返回一个或多个位置元素的 Geohash 表示。
	 * 
	 * 
	 * @see link http://redis.cn/commands/geohash.html
	 * @param key
	 * @param members
	 * @return 一个数组， 数组的每个项都是一个 geohash 。 命令返回的 geohash 的位置与用户给定的位置元素的位置一一对应。
	 */
	public List<String> geohash(String key, String... members) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geohash(key, members);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 从key里返回所有给定位置元素的位置（经度和纬度）。
	 * 
	 * 
	 * @see link http://redis.cn/commands/geopos.html
	 * @param key
	 * @param members
	 * @return
	 */
	public List<GeoCoordinate> geopos(String key, String... members) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.geopos(key, members);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。
	 * 
	 * 
	 * @see link http://redis.cn/commands/georadius.html
	 * @param key
	 * @param longitude
	 * @param latitude
	 * @param radius
	 *            半径
	 * @param unit
	 *            m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺。
	 * @return
	 */
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.georadius(key, longitude, latitude, radius, unit);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。
	 * 
	 * 
	 * @see link http://redis.cn/commands/georadius.html
	 * @param key
	 * @param longitude
	 * @param latitude
	 * @param radius
	 *            半径
	 * @param unit
	 *            m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺。
	 * @param param
	 *            <br>
	 *            WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
	 *            <br>
	 *            WITHCOORD: 将位置元素的经度和维度也一并返回。 <br>
	 *            WITHHASH: 以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。
	 *            这个选项主要用于底层应用或者调试， 实际中的作用并不大。 命令默认返回未排序的位置元素。 通过以下两个参数，
	 *            用户可以指定被返回位置元素的排序方式： <br>
	 *            ASC: 根据中心的位置， 按照从近到远的方式返回位置元素。 <br>
	 *            DESC: 根据中心的位置， 按照从远到近的方式返回位置元素。
	 * @return
	 */
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.georadius(key, longitude, latitude, radius, unit, param);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 这个命令和 GEORADIUS 命令一样， 都可以找出位于指定范围内的元素， 但是 GEORADIUSBYMEMBER 的中心点是由给定的位置元素决定的，
	 * 而不是像 GEORADIUS 那样， 使用输入的经度和纬度来决定中心点 指定成员的位置被用作查询的中心。
	 * 
	 * 
	 * @see link http://redis.cn/commands/georadiusByMember.html
	 * @param key
	 * @param member
	 * @param radius
	 *            半径
	 * @param unit
	 *            m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺。
	 * @return
	 */
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.georadiusByMember(key, member, radius, unit);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}

	/**
	 * 这个命令和 GEORADIUS 命令一样， 都可以找出位于指定范围内的元素， 但是 GEORADIUSBYMEMBER 的中心点是由给定的位置元素决定的，
	 * 而不是像 GEORADIUS 那样， 使用输入的经度和纬度来决定中心点 指定成员的位置被用作查询的中心。
	 * 
	 * 
	 * @see link http://redis.cn/commands/georadiusByMember.html
	 * @param key
	 * @param member
	 * @param radius
	 *            半径
	 * @param unit
	 *            m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺。
	 * @param param
	 *            <br>
	 *            WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
	 *            <br>
	 *            WITHCOORD: 将位置元素的经度和维度也一并返回。 <br>
	 *            WITHHASH: 以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。
	 *            这个选项主要用于底层应用或者调试， 实际中的作用并不大。 命令默认返回未排序的位置元素。 通过以下两个参数，
	 *            用户可以指定被返回位置元素的排序方式： <br>
	 *            ASC: 根据中心的位置， 按照从近到远的方式返回位置元素。 <br>
	 *            DESC: 根据中心的位置， 按照从远到近的方式返回位置元素。
	 * @return
	 */
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getResource();
			return shardedJedis.georadiusByMember(key, member, radius, unit, param);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			log.error(e.getMessage(), e);
			throw new JedisException(e);
		} finally {
			returnResource(shardedJedis);
		}
	}
	
}
