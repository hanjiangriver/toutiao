package com.zhj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhj.dao.MessageDAO;
import com.zhj.model.Message;

@Service
public class MessageService {

	@Autowired
	MessageDAO messageDAO;
	
	public int addMessage(Message message){
	 return	messageDAO.addMessage(message);
	}
	
	public List<Message> getConversationDetail(String conversationId,int offset,int limit){
		return messageDAO.getConversationDetail(conversationId, offset, limit);
	}
	
	public List<Message> getConversationList(int userId,int offset,int limit){
		return messageDAO.getConversationList(userId, offset, limit);
	}
	
	public int  getConversationUnReadCount(int userId,String conversationId){
		return messageDAO.getConversationUnReadCount(userId, conversationId);
	}
}
