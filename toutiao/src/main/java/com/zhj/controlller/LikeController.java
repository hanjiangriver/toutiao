package com.zhj.controlller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhj.ToutiaoUtil.ToutiaoUtil;
import com.zhj.async.EventModel;
import com.zhj.async.EventProducer;
import com.zhj.async.EventType;
import com.zhj.model.EntityType;
import com.zhj.model.HostHolder;
import com.zhj.model.News;
import com.zhj.service.LikeService;
import com.zhj.service.NewsService;

@Controller
public class LikeController {

	@Autowired
	HostHolder hostHolder;
	@Autowired
	LikeService likeService;

	@Autowired
	NewsService newsService;

	@Autowired
	EventProducer eventProducer;
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
	
	@RequestMapping(path = { "/like" }, method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
	public String like(Model model, @RequestParam("newsId") int newsId) {
		try {
			int userid = hostHolder.getUser().getId();
			//int userid=26;
			
			long likeCount = likeService.like(userid, EntityType.ENTITY_NEWS, newsId);
			News news=newsService.getById(newsId);
			newsService.updateLikeCount(newsId, (int) likeCount);
			//干完点赞之前 发一个事件出来异步处理
			 eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
					 .setEntityId(newsId).setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(news.getUserId()));
			return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.equals("用户为空"+e.getMessage());
			return ToutiaoUtil.getJSONString(1, "用户为空");
		}
		
	}

	@RequestMapping(path = { "/dislike" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String dislike(Model model, @RequestParam("newsId") int newsId) {
		int userid = hostHolder.getUser().getId();
		long likeCount = likeService.disLike(userid, EntityType.ENTITY_NEWS, newsId);
		newsService.updateLikeCount(newsId, (int) likeCount);
		return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
	}
}
