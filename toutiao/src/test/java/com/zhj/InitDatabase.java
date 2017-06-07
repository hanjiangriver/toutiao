package com.zhj;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.fabric.xmlrpc.base.Data;
import com.zhj.dao.LoginTicketDAO;
import com.zhj.dao.NewsDAO;
import com.zhj.dao.UserDAO;
import com.zhj.model.LoginTicket;
import com.zhj.model.News;
import com.zhj.model.User;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)

public class InitDatabase {

	@Autowired 
	UserDAO userDAO; 
	@Autowired
	NewsDAO newsDAO;
	@Autowired 
	LoginTicketDAO loginTicketDAO;
	@Test
	public void initData() {
		Random random=new Random();
		for(int i=1;i<20;i++){
//			User user=new User();
//			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000) ));
//			user.setName(String.format("user%d", i));
//			user.setPassword(String.format("password%d", i));
//			user.setSalt("");
//			userDAO.addUser(user);
//			
//			user.setPassword("nowcoder");
//			userDAO.updtePassword(user);
			
//			News news=new News();
//			news.setCommentCount(i);
			Date date=new Date();
//			date.setTime(date.getTime()+1000*3600*5*i);
//			news.setCreateDate(date);
//			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000) ));
//			news.setLikeCount(i+1);
//			news.setUserId(i);
//			news.setTitle(String.format("TITLE{%d}", i));
//			news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
//			newsDAO.addNews(news);
			
			
//			LoginTicket ticket=new LoginTicket();
//			ticket.setStatus(0);
//			ticket.setUserId(i+1);
//			ticket.setExpired(date);
//			ticket.setTicket(String.format("TICKET%d", i+1));
//			loginTicketDAO.addTicket(ticket);
//			
//			loginTicketDAO.updteStatus(ticket.getTicket(), 2);
			
		}
			
		loginTicketDAO.updteStatus("TICKET2", 2);
		Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET2").getUserId());
//		Assert.assertEquals("nowcoder", userDAO.selectById(6).getPassword());
//		userDAO.deleteById(1);
//		Assert.assertNull(userDAO.selectById(1));

	
	}
	
}
