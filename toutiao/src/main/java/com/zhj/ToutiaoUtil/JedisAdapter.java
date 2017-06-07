package com.zhj.ToutiaoUtil;

import java.util.List;

import javax.validation.constraints.Null;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhj.controlller.LoginController;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

@Service
public class JedisAdapter implements InitializingBean {
	
	 /**** public static void main(String[] args ){ Jedis jedis=new Jedis();
	 * jedis.flushAll(); jedis.set("hello", "world");
	 * print(1,jedis.get("hello")); jedis.rename("hello", "newhello");
	 * print(2,jedis.get("newhello")); jedis.setex("hello2", 15, "world2");
	 * 
	 * 
	 * jedis.set("pv", "100"); jedis.incr("pv"); print(3,jedis.get("pv"));
	 * jedis.incrBy("pv", 5); print(4,jedis.get("pv")); //jedis
	 * 
	 * String listname="list"; for(int i=0;i<10;i++){ jedis.lpush(listname,
	 * "a"+String.valueOf(i)); } print(5,jedis.lrange(listname, 0, 10));
	 * print(6,jedis.llen(listname)); print(7,jedis.lpop(listname));
	 * print(8,jedis.lrange(listname, 0, 10)); print(9,jedis.lindex(listname,
	 * 3)); print(10,jedis.linsert(listname, LIST_POSITION.BEFORE, "a3", "b3"));
	 * print(11,jedis.lrange(listname, 0, 10));
	 * 
	 * 
	 * String uerkey="user1"; jedis.hset(uerkey, "name", "jim");
	 * jedis.hsetnx(uerkey, "age", "12"); print(12,jedis.hget(uerkey, "name"));
	 * print(13,jedis.hgetAll(uerkey)); jedis.hdel(uerkey, "name");
	 * print(14,jedis.hgetAll(uerkey)); print(15,jedis.hkeys(uerkey));
	 * print(16,jedis.hvals(uerkey)); print(16,jedis.hexists(uerkey, "name"));
	 * print(16,jedis.hexists(uerkey, "age")); jedis.hsetnx(uerkey, "name",
	 * "jim"); print(17,jedis.hkeys(uerkey));
	 * 
	 * String likeKeys1="newLike1"; String likeKeys2="newLike2"; for(int
	 * i=0;i<10;i++){ jedis.sadd(likeKeys1, String.valueOf(i));
	 * jedis.sadd(likeKeys2, String.valueOf(i*2)); }
	 * print(18,jedis.smembers(likeKeys1)); print(19,jedis.smembers(likeKeys2));
	 * print(20,jedis.sinter(likeKeys1,likeKeys2));
	 * print(21,jedis.sdiff(likeKeys1,likeKeys2));
	 * print(22,jedis.sunion(likeKeys1,likeKeys2)); jedis.srem(likeKeys1, "5");
	 * print(23,jedis.smembers(likeKeys1)); jedis.smove(likeKeys2, likeKeys1,
	 * "14"); print(18,jedis.smembers(likeKeys1));
	 * 
	 * 
	 * //sorted set String rankkey="rankkey"; jedis.zadd(rankkey, 15, "jim");
	 * jedis.zadd(rankkey, 60, "ben"); jedis.zadd(rankkey, 75, "lee");
	 * jedis.zadd(rankkey, 80, "mei"); jedis.zadd(rankkey, 90, "lucy");
	 * 
	 * print(24,jedis.zcount(rankkey, 70, 90)); print(25,jedis.zscore(rankkey,
	 * "mei")); print(25,jedis.zincrby(rankkey, 2,"mei"));
	 * print(26,jedis.zrank(rankkey, "lucy")); print(26,jedis.zrevrange(rankkey,
	 * 0, 100)); print(26,jedis.zrevrank(rankkey, "lucy")); for(Tuple
	 * tuple:jedis.zrangeByScoreWithScores(rankkey, 0, 100)){
	 * print(27,tuple.getElement()+tuple.getScore()); }
	 * 
	 * JedisPool pool=new JedisPool(); for(int i=0;i<100;i++){ Jedis
	 * j=pool.getResource(); j.get("a"); System.out.print("pool"+i); j.close();
	 * } } public static void print(int index,Object obj){
	 * System.out.println(String.format("%d,%s", index,obj.toString())); }**/
	 
	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	private JedisPool pool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool = new JedisPool("localhost", 6379);
	}

	public Jedis getJedis() {
		return pool.getResource();
	}

	public long sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long srem(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发生异常" + e.getMessage());
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 将对象序列化 存入数据库中
	public void setObject(String key, Object obj) {
		set(key, JSON.toJSONString(obj));
	}

	// 反序列化
	public <T> T getObject(String key, Class<T> clazz) {
		String value = get(key);
		if (value != null) {
			return JSON.parseObject(value, clazz);
		}
		return null;
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
			//return getJedis().get(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
	 public List<String> brpop(int timeout, String key) {
	        Jedis jedis = null;
	        try {
	            jedis = pool.getResource();	  
	            return jedis.brpop(timeout, key);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return null;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	}

}
