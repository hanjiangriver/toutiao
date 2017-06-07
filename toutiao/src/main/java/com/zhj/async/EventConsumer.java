package com.zhj.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhj.ToutiaoUtil.JedisAdapter;
import com.zhj.ToutiaoUtil.RedisKeyUtil;
import com.zhj.aspect.LogAspect;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
	@Autowired
	JedisAdapter jedisAdapter;
	private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
	private ApplicationContext applicationContext;
	private Map<EventType, List<EventHandler>> config = new HashMap<>();

	//在bean初始化的时候就调用该函数
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// 把所有实现了EventHandler 接口的类都找出来
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if (beans != null) {
			for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
				List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
				for (EventType type : eventTypes) {
					if (!config.containsKey(type)) {
						config.put(type, new ArrayList<EventHandler>());
					}
					config.get(type).add(entry.getValue());// 把同一类型的 不同处理实践加到arraylist里面															
				}
			}
		}
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					String key = RedisKeyUtil.getEventQueueKey();
					List<String> events = jedisAdapter.brpop(0, key);// brpop从右边开始取每次取一个键和一个值																		 
					for (String message : events) {
						if (message.equals(key)) {// 这种key不处理 因为我要取值而不是键
							continue;
						}
						// 反序列化
						EventModel eventModel = JSON.parseObject(message, EventModel.class);
						if (!config.containsKey(eventModel.getType())) {
							logger.error("不能识别的事件");
							continue;
						}
						for (EventHandler eventHandler : config.get(eventModel.getType())) {
							eventHandler.doHandle(eventModel);
						}

					}

				}
			}
		});
		thread.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
	}

}
