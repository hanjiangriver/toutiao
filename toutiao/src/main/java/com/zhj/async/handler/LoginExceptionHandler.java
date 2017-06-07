package com.zhj.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhj.ToutiaoUtil.MailSender;
import com.zhj.async.EventHandler;
import com.zhj.async.EventModel;
import com.zhj.async.EventType;
import com.zhj.model.Message;
import com.zhj.service.MessageService;

@Component
public class LoginExceptionHandler implements EventHandler{

	@Autowired
	MessageService messageService;
		
	@Autowired
	MailSender mailSender;
	
	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
		//判断是否有登录异常
		Message message=new Message();
		message.setContent("你上次登录的ip异常");
		message.setFromId(3);
		message.setCreatedDate(new Date());
		
		messageService.addMessage(message);
		Map<String, Object>map=new HashMap<>();
		map.put("username", model.getExt("username"));
		mailSender.sendWithHTMLTemplate(model.getExt("email"), "欢迎登录", "mails/welcome.html", map);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LOGIN);
	}

}
