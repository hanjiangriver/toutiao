package com.zhj.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhj.async.EventHandler;
import com.zhj.async.EventModel;
import com.zhj.async.EventType;
import com.zhj.model.Message;
import com.zhj.model.User;
import com.zhj.service.MessageService;
import com.zhj.service.UserService;

@Component
public class LikeHandler implements EventHandler {

	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
      Message message=new Message();
      message.setFromId(3);
      User user=userService.getUser(model.getActorId());
      message.setContent("用户"+user.getName()+"赞了你的资讯，http：//127.0.0.1:8080/news/"+model.getEntityId());
      message.setCreatedDate(new Date());
      message.setToId(model.getEntityOwnerId());
      message.setConversationId(
				3 < model.getEntityOwnerId() ? String.format("%d_%d", 3, model.getEntityOwnerId()) : String.format("%d_%d", model.getEntityOwnerId(), 3));
      message.setHasRead(0);
      messageService.addMessage(message);
      //System.out.println("我喜欢");
	}

	//我对什么感兴趣，感兴趣的都注册进来
	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LIKE);
		//return Arrays.asList(EventType.LIKE,EventType.COMMENT);
		
	}

}
