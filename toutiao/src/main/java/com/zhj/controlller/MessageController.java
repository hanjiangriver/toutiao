package com.zhj.controlller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.zhj.dao.MessageDAO;
import com.zhj.model.HostHolder;
import com.zhj.model.Message;
import com.zhj.model.User;
import com.zhj.model.ViewObject;
import com.zhj.service.MessageService;
import com.zhj.service.UserService;

@Controller
public class MessageController {

	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired 
	HostHolder hostHolder;
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@RequestMapping(path = { "/msg/addMessage" }, method = { RequestMethod.POST })
	@ResponseBody
	public String addMessage(@RequestParam("fromId") int fromId, @RequestParam("toId") int toId,
			@RequestParam("content") String content) {
		try {
			Message message = new Message();
			message.setContent(content);
			message.setFromId(fromId);
			message.setToId(toId);
			message.setCreatedDate(new Date());
			message.setConversationId(
					fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
			messageService.addMessage(message);
			return ToutiaoUtil.getJSONString(message.getId());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("增加评论失败" + e.getMessage());
			return ToutiaoUtil.getJSONString(1, "插入评论失败");
		}

	}

	@RequestMapping(path = { "/msg/detail" }, method = { RequestMethod.GET })
	public String conversationDetail(@RequestParam("conversationId") String conversationId, Model model) {
		try {
			List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
			List<ViewObject> messages = new ArrayList<>();
			for (Message msg : conversationList) {
				ViewObject vo = new ViewObject();
				vo.set("message", msg);
				User user = userService.getUser(msg.getFromId());
				if (user == null) {
					continue;
				}
				vo.set("headUrl", user.getHeadUrl());
				vo.set("userId", user.getId());
				messages.add(vo);
			}
			model.addAttribute("messages", messages);
			return "letterDetail";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取详情消息失败" + e.getMessage());
			return ToutiaoUtil.getJSONString(1, "获取详情失败");
		}

	}
	@RequestMapping(path = { "/msg/list" }, method = { RequestMethod.GET })
	public String conversationList(Model model) {
		try {
			int localUserId=hostHolder.getUser().getId();
			List<ViewObject>conversations=new ArrayList<>();		
			List<Message> conversationList=messageService.getConversationList(localUserId, 0, 10);
			for (Message msg : conversationList) {
				ViewObject vo = new ViewObject();
				vo.set("conversation", msg);
				int targetId=msg.getFromId()==localUserId?msg.getToId():msg.getFromId();
				User user = userService.getUser(targetId);
				vo.set("user", user);
				vo.set("unreadCount", messageService.getConversationUnReadCount(localUserId, msg.getConversationId()));
				conversations.add(vo);
			}
			model.addAttribute("conversations", conversations);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取站内信列表失败" + e.getMessage());
			return ToutiaoUtil.getJSONString(1, "获取站内信失败");
		}
		return "letter";
		
	}
}
