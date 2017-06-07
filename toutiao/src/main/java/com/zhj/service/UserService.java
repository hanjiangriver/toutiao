package com.zhj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhj.ToutiaoUtil.ToutiaoUtil;
import com.zhj.dao.LoginTicketDAO;
import com.zhj.dao.UserDAO;
import com.zhj.model.LoginTicket;
import com.zhj.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

@Service
public class UserService {
    @Autowired
	private UserDAO userDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    Random random=new Random();
    public User getUser(int id){
    	return userDAO.selectById(id);
    }
    public Map<String, Object> register(String username,String password){
    	Map<String, Object>map=new HashMap<>();
    	if(StringUtils.isBlank(username)){
    		map.put("msgname", "用户名不能为空") ;
    		return map;
    	}
    	if(StringUtils.isBlank(password)){
    		map.put("msgpwd", "用户名不能为空") ;
    		return map;
    	}
    	User user=userDAO.selectByName(username);
    	if(user!=null){
    		map.put("msgname", "用户名已被注册") ;
    		return map;
    	}
    	user=new User();
    	user.setName(username);
    	user.setSalt(UUID.randomUUID().toString().substring(0, 5));
    	user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000) ));
    	user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
    	userDAO.addUser(user);
    	//登录
    	String ticket=addLoginTicket(user.getId());
    	map.put("ticket", ticket);
    	return map;
    }
    
    
    
    public Map<String, Object> login(String username,String password){
    	Map<String, Object>map=new HashMap<>();
    	if(StringUtils.isBlank(username)){
    		map.put("msgname", "用户名不能为空") ;
    		return map;
    	}
    	if(StringUtils.isBlank(password)){
    		map.put("msgpwd", "用户名不能为空") ;
    		return map;
    	}
    	User user=userDAO.selectByName(username);
    	if(user==null){
    		map.put("msgname", "用户名不存在") ;
    		return map;
    	}
    	if(!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
    		map.put("msgpwd", "密码不正确") ;
    		return map;
    	}

      //ticket
    	map.put("userId", user.getId());
   	    String ticket=addLoginTicket(user.getId());
     	map.put("ticket", ticket);
    	return map;
    }
    
    
    private String addLoginTicket(int userId){
    	LoginTicket ticket=new LoginTicket();
		ticket.setStatus(0);
		ticket.setUserId(userId);
		Date date=new Date();
		date.setTime(date.getTime()+1000*3600*24);
		ticket.setExpired(date);
		ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(ticket);
		return ticket.getTicket();
    }
    public void logout(String ticket){
    	loginTicketDAO.updteStatus(ticket, 1);//把ticket 过期
    }
    
}
