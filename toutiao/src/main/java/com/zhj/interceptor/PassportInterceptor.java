package com.zhj.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhj.dao.LoginTicketDAO;
import com.zhj.dao.UserDAO;
import com.zhj.model.HostHolder;
import com.zhj.model.LoginTicket;
import com.zhj.model.User;

@Component
public class PassportInterceptor implements HandlerInterceptor{

	@Autowired
	LoginTicketDAO loginTicketDAO;
	@Autowired 
	UserDAO userDAO;
	@Autowired 
	HostHolder hostHolder;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		hostHolder.clear();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		if(arg3!=null&&hostHolder.getUser()!=null){
			arg3.addObject("user", hostHolder.getUser());
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String ticket=null;
		if(arg0.getCookies()!=null){
			for(Cookie cookie:arg0.getCookies()){
				if(cookie.getName().equals("ticket")){
					ticket=cookie.getValue();
					break;
				}
			}
		}
		if(ticket!=null){
			LoginTicket loginTicket=loginTicketDAO.selectByTicket(ticket);
			if(loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!= 0){
				return true;
			}
			User user=userDAO.selectById(loginTicket.getUserId());
			hostHolder.setUser(user);
		}
		return true;
	}

}
