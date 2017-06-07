package com.zhj.controlller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.zhj.dao.NewsDAO;
import com.zhj.model.EntityType;
import com.zhj.model.HostHolder;
import com.zhj.model.News;
import com.zhj.model.ViewObject;
import com.zhj.service.LikeService;
import com.zhj.service.NewsService;
import com.zhj.service.UserService;

@Controller
public class HomeController {

	@Autowired 
	NewsService newsService;
	@Autowired 
	UserService userService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	LikeService likeService;
//	@RequestMapping(path={"/","/index"},method={RequestMethod.GET,RequestMethod.POST})
//
//	 public String index(Model model){
//		model.addAttribute("vos",getNews(0,0,10));
//		 return "home";
//	 }
	
	@RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getNews(0, 0, 10));
        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }
	private List<ViewObject> getNews(int uerId,int offset,int limit){
        List<News>newsList=newsService.getLastestNews(uerId, offset, limit);
		int localUserId=(hostHolder.getUser()!=null)?hostHolder.getUser().getId():0;
		List<ViewObject>vos=new ArrayList<>();
		for(News news:newsList){
			ViewObject vo=new ViewObject();
			vo.set("news", news);
			vo.set("user",userService.getUser(news.getUserId()));
			if(localUserId!=0){
				vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
			}else{
				vo.set("like",0);
			}			
			vos.add(vo);
		}
		return vos;
	}
	
	@RequestMapping(path={"/user/{userId}"},method={RequestMethod.GET,RequestMethod.POST})

	 public String userIndex(Model model,@PathVariable("userId") int userId ,@RequestParam(value="pop",defaultValue="0") int pop){
	
		model.addAttribute("vos",getNews(userId,0,10));
		model.addAttribute("pop", pop);
		 return "home";
	}
 }
