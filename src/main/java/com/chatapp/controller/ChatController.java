package com.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.chatapp.model.Chat;
import com.chatapp.model.UserProfile;
import com.chatapp.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@RequestMapping(value="/chatBox")
public class ChatController {

	@Autowired 
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private UserService userService;
	
	/**
	@MessageMapping("/chat-websocket")
	@SendTo("/topic/greetings")
	public Chat sayHi(Chat chat) throws Exception {
		System.out.println("in the chat controller - sayHi method");
		
		//Thread.sleep(1000);
		System.out.println(chat.getChatMessage());
		return new Chat(chat.getChatMessage());
		
		//return user.getName().toString() + " :  " + chat.getChatMessage().toString();		
	}
	**/
	

	@MessageMapping("/chat-websocket")
	public void sendReply(Chat chat) {

		System.out.println("in the send reply method");		
		
		if(chat.getToUsername().equals("allusersgroup")) {
			System.out.println("chat = " + chat.getToUsername() + "  " + chat.getFromUsername() + "  " + chat.getChatMessage());
			
			System.out.println( "simpMessagingTemplate.getDefaultDestination() = " + simpMessagingTemplate.getDefaultDestination()
			
					+ "\n simpMessagingTemplate.getUserDestinationPrefix() = " + simpMessagingTemplate.getUserDestinationPrefix()
					
					+ "\n  simpMessagingTemplate.getMessageChannel()  = " + simpMessagingTemplate.getMessageChannel() 
					+ "\n  simpMessagingTemplate.getMessageConverter()  = " + simpMessagingTemplate.getMessageConverter()
					);
			
			//simpMessagingTemplate.convertAndSend("/allusersgroup", chat);
			simpMessagingTemplate.convertAndSendToUser("allusersgroup","/reply", chat);
		}
		else {
			System.out.println("chat = " + chat.getToUsername() + "  " + chat.getFromUsername() + "  " + chat.getChatMessage());
			simpMessagingTemplate.convertAndSendToUser(chat.getToUsername(),"/reply", chat);
		}
		
		
	}
	
	@RequestMapping("/chatBox")
	public ModelAndView welcome(HttpServletRequest request) {	
		ModelAndView mv =  new ModelAndView("chatBox/chatBox");		
		List<UserProfile> users = getAllUserList();
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		if(username != null)	{
			users.removeIf(x -> x.getUsername().equals(username));
			System.out.println("users = "+ users);
			mv.addObject("userList",users);
			mv.addObject("currentUser", username);
		}
		else {
			mv.setViewName("error");
		}

		return mv;
	}
	
	public List<UserProfile> getAllUserList() {
		List<UserProfile> user = null;
		try {
			user = userService.listUser();
		}
		catch(Exception ex) {
			System.out.println("Exception = "+ex);
		}
		finally {
			return user;
		}		
	}
	
	@RequestMapping("/GetAllUserList")
	@ResponseBody
	public List<UserProfile> getAllUserList(HttpServletRequest request) {
				
		List<UserProfile> users = null;
		
		try {
			users = getAllUserList();			
		}
		catch(Exception ex) {
			System.out.println("Exception : " + ex);
		}
		finally {
			return users;
		}
		
	}
	
}
