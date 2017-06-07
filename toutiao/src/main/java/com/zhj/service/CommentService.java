package com.zhj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhj.dao.CommentDAO;
import com.zhj.model.Comment;

@Service
public class CommentService {

	@Autowired 
	private CommentDAO commentDAO;
	public List<Comment> getCommentByEntity(int entityId,String entityType){
		return commentDAO.selectByEntity(entityId, entityType);
	}
	
	public int addComment(Comment comment){
		return commentDAO.addComment(comment);
	}
	
	public int getCommentCount(int entityId,String entityType){
		return commentDAO.getCommentCount(entityId, entityType);
	}
}
