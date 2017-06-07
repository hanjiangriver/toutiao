package com.zhj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhj.dao.LoginTicketDAO;
import com.zhj.dao.UserDAO;
import com.zhj.model.HostHolder;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

	@Autowired
	LoginTicketDAO loginTicketDAO;
	@Autowired 
	UserDAO userDAO;
	@Autowired 
	HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(hostHolder.getUser()==null){//弹出登录框
			response.sendRedirect("/?pop=1");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
