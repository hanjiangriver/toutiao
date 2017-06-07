package com.zhj.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhj.ToutiaoUtil.JedisAdapter;
import com.zhj.ToutiaoUtil.RedisKeyUtil;

@Service
public class EventProducer {
	@Autowired
	JedisAdapter jedisAdapter;

	public boolean fireEvent(EventModel model) {
		try {
			String json = JSONObject.toJSONString(model);
			String key = RedisKeyUtil.getEventQueueKey();
			jedisAdapter.lpush(key, json);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}
}
