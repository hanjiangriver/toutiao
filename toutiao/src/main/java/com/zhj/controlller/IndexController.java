package com.zhj.controlller;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhj.aspect.LogAspect;
import com.zhj.model.User;
import com.zhj.service.TouTiaoService;

import  java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;;


//@Controller
public class IndexController {
	private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);
@Autowired
private TouTiaoService toutiaoservice;
 @RequestMapping("/")
 @ResponseBody
 public String index(){
	 logger.info("visit index");
	 return " zhj hello word"+toutiaoservice.say();
 }

 
 @RequestMapping(value={"/profile/{groupId}/{userId}"})
 @ResponseBody
 public String profile(@PathVariable("groupId") String groupId,
		               @PathVariable("userId") int userId,
		               @RequestParam(value="key",defaultValue="1")int key,
		               @RequestParam(value="type",defaultValue="zhj")String type){
	 return String.format("{%s},{%d},{%d},{%s}", groupId,userId,key,type);
 }
 @RequestMapping(value={"/vm"})
 public String news(Model model)
 {
	 model.addAttribute("value1", "张汉江");
	 List<String>colors=Arrays.asList(new String[]{"RED","GREEN","BLUE"});
	 Map<String, String>map=new HashMap<String, String>();
	 for(int i=0;i<4;i++){
		 map.put(String.valueOf(i), String.valueOf(i*i));
	 }
	 model.addAttribute("colors", colors);
	 model.addAttribute("map", map);
	// model.addAttribute("user", new User("jim"));
	 return "news";
 }
   
 @RequestMapping(value={"/request"})
 @ResponseBody
 public String requestTest(HttpServletRequest request,HttpServletResponse response,
		                   HttpSession session){
	 StringBuilder sBuilder=new StringBuilder();
	 Enumeration<String> headername=request.getHeaderNames();
	 while(headername.hasMoreElements()){
		String name= headername.nextElement();
		sBuilder.append(name+":"+request.getHeader(name)+"<br>");
	 }
	 for(Cookie cookie:request.getCookies()){
		 sBuilder.append("Cookie:");
		 sBuilder.append(cookie.getName());
		 sBuilder.append(cookie.getValue());
		 sBuilder.append("</br>");
	 }
	
	 return sBuilder.toString();
 }
 @RequestMapping(value={"/reponse"})
 @ResponseBody
 public String response(@CookieValue(value="newcoder",defaultValue="a") String newcoder,
		                 @RequestParam(value="key",defaultValue="key") String key,
		                 @RequestParam(value="value",defaultValue="value") String value,
		                 HttpServletResponse response){
	 
	 response.addCookie(new Cookie(key, value));
	 response.addHeader(key, value);
	 return "fsadfaf"+newcoder;
 }
}






