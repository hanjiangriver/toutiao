package com.zhj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhj.ToutiaoUtil.JedisAdapter;
import com.zhj.ToutiaoUtil.RedisKeyUtil;

@Service
public class LikeService {
	@Autowired
	JedisAdapter jedisAdapter;

	/**
	 * 如果喜欢返回1 如果不喜欢返回-1 否则返回0
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public int getLikeStatus(int userId, String entityType, int entityId) {
		String likKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		if (jedisAdapter.sismember(likKey, String.valueOf(userId))) {
			return 1;
		}
		String disLikKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		if (jedisAdapter.sismember(disLikKey, String.valueOf(userId))) {
			return -1;
		}
		return 0;
	}

	public long like(int userId, String entityType, int entityId) {
		String likKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		String disLikKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		jedisAdapter.sadd(likKey, String.valueOf(userId));
		jedisAdapter.srem(disLikKey, String.valueOf(userId));
		return jedisAdapter.scard(likKey);
	}
	
	public long disLike(int userId, String entityType, int entityId) {
		String likKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		String disLikKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		jedisAdapter.sadd(disLikKey, String.valueOf(userId));
		jedisAdapter.srem(likKey, String.valueOf(userId));
		return jedisAdapter.scard(likKey);
	}
}
