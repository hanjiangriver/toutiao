package com.zhj.controlller;

import java.nio.file.Path;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhj.ToutiaoUtil.ToutiaoUtil;
import com.zhj.aspect.LogAspect;
import com.zhj.async.EventModel;
import com.zhj.async.EventProducer;
import com.zhj.async.EventType;
import com.zhj.service.UserService;

@Controller
public class LoginController {
  
	private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserService userService;
	@Autowired
	EventProducer eventProducer;
	
	@RequestMapping(path={"/reg/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	 public String reg(Model model,@RequestParam("username") String username,
			                   @RequestParam("password")String password ,
			                   @RequestParam(value="rember",defaultValue="0")int  remeberme ,
			                   HttpServletResponse reponse) {
	
		try{
			Map<String, Object>map=userService.register(username, password);
			if(map.containsKey("ticket")){
				Cookie cookie=new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if(remeberme>0){
					cookie.setMaxAge(3600*24*5);
				}
				reponse.addCookie(cookie);
				
				return ToutiaoUtil.getJSONString(0, "注册成功");
			}
			
			else {
				return ToutiaoUtil.getJSONString(1, map);
			}
			
		}catch(Exception e){
			logger.error("注册异常"+e.getMessage());;
			return ToutiaoUtil.getJSONString(1, "注册异常");
		}
		
		
	}
	@RequestMapping(path={"/login/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	 public String login(Model model,@RequestParam("username") String username,
			                   @RequestParam("password")String password ,
			                   @RequestParam(value="rember",defaultValue="0")int  remeberme,
			                   HttpServletResponse response) {
	
		try{
			Map<String, Object>map=userService.login(username, password);
			if(map.containsKey("ticket")){
				Cookie cookie=new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if(remeberme>0){
					cookie.setMaxAge(3600*24*5);
				}
				 response.addCookie(cookie);
				 //发邮件
			   eventProducer.fireEvent(new EventModel(EventType.LOGIN).setActorId((int)map.get("userId"))
					     .setExt("username", username).setExt("email", "573449958@qq.com"));
				return ToutiaoUtil.getJSONString(0, "登录成功");
			}
			
			else {
				return ToutiaoUtil.getJSONString(1, map);
			}
			
		}catch(Exception e){
			logger.error("登录异常"+e.getMessage());;
			return ToutiaoUtil.getJSONString(1, "登录异常");
		}
				
	}
	@RequestMapping(path={"/logout/"},method={RequestMethod.GET,RequestMethod.POST})
	 public String logout(@CookieValue("ticket")String ticket){
		 userService.logout(ticket);
		 return "redirect:/";
	}
	
}
