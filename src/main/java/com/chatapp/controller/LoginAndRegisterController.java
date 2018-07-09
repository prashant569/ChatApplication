package com.chatapp.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatapp.model.UserProfile;
import com.chatapp.service.UserService;


@Controller
@RequestMapping(value="/login")
public class LoginAndRegisterController {

	@Autowired
	UserService userService;
	
	@Autowired
	ChatController chatController;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showLoginForm1() {
		ModelAndView mv = new ModelAndView();
		System.out.println(" in the showLoginForm method");
		mv.addObject("userProfile",new UserProfile());
		mv.addObject("loginFormClassValue","active");
		mv.setViewName("LoginAndRegister/LoginAndRegister");
		return mv;
	}
	
	@RequestMapping(value="/register")
	public ModelAndView showRegisterForm() {
		ModelAndView mv = new ModelAndView();
		System.out.println(" in the showRegisterForm method");
		mv.addObject("userProfile",new UserProfile());
		mv.addObject("registerFormClassValue","active");
		mv.setViewName("LoginAndRegister/LoginAndRegister");
		return mv;
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public ModelAndView showLoginForm(HttpServletRequest request) {
		
		if(request.getSession().getAttribute("username")!=null)	{
			System.out.println("returning to chatbox");
			return new ModelAndView("redirect:/chatBox/chatBox");
		}
		
		ModelAndView mv = new ModelAndView();
		System.out.println(" in the showLoginForm method");
		mv.addObject("userProfile",new UserProfile());
		mv.addObject("loginFormClassValue","active");
		mv.setViewName("LoginAndRegister/LoginAndRegister");
		return mv;
	}
	
	
	@RequestMapping("/userLogin")
	public void login(@ModelAttribute UserProfile userProfile,HttpServletRequest request,HttpServletResponse response) throws IOException, ScriptException {
		
		 RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		
		System.out.println(" in the login method");

		UserProfile loggedinUser = userService.findUserByUsername(userProfile.getUsername());
		
		System.out.println(" username = " + userProfile.getUsername() 
		+ " firstName = " + loggedinUser.getFirstName()
		+ "lastName = " + loggedinUser.getLastName());
		
		// sha 256 password encryption using Apache Commons Codec � 1.9
		String passwordSha256Hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userProfile.getPassword()); 
		
		System.out.println("hashed password  = " +   passwordSha256Hex);

		boolean isMatched = userService.checkCredentials(userProfile.getUsername(), passwordSha256Hex);
		
		String targetUrl = null;
		
		if(isMatched) {
			HttpSession session = request.getSession();
			session.setAttribute("username", userProfile.getUsername());
			session.setAttribute("firstName", loggedinUser.getFirstName());
			session.setAttribute("lastName",loggedinUser.getLastName());
			session.setAttribute("isAdmin", loggedinUser.getIsAdmin());
			
			System.out.println(" in java isAdmin = " + loggedinUser.getIsAdmin());
			
			userService.updateUserState(userProfile.getUsername(),true);			
			targetUrl = "/chatBox/chatBox";
			
		}
		else {
			System.out.println("credentials not matched");
			targetUrl = "/LoginAndRegister/LoginAndRegister";
		
			
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
		
	}
	
	
	@RequestMapping("/userRegister")
	public String register(@ModelAttribute UserProfile userProfile) {
		
		System.out.println(" in the register method");

		System.out.println(" username = " + userProfile.getUsername() + " password = " + userProfile.getPassword());
		
		// sha 256 password encryption using Apache Commons Codec � 1.9
		String passwordSha256Hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userProfile.getPassword()); 
		
		System.out.println("hashed password  = " +   passwordSha256Hex);
		
		userService.add(new UserProfile(userProfile.getFirstName(),userProfile.getLastName(),userProfile.getUsername(), passwordSha256Hex));

		return "redirect:/login/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		System.out.println(" in the logout method");
		userService.updateUserState(request.getSession().getAttribute("username").toString(),false);
		request.getSession().invalidate();	
		
		return "redirect:/";
	}
	
	@RequestMapping("/checkForExistingUsername")
	@ResponseBody
	public String checkForExistingUsername(@RequestParam("username") String username) {
		
		UserProfile user = null;		
		String errorMessage = null;
		
		try {
			user = userService.findUserByUsername(username);
		}
		catch (Exception ex) {
			System.out.println("Exception :" + ex);
			return "Something unexpected happen. Please try again";
		}
		
		finally {
			System.out.println(" user = " + user.getUsername() + "   " + user.getPassword());
			if(user != null) {
				errorMessage = "Username already exist";
			}
			return errorMessage;
		}	
	}
	
	@RequestMapping(value="/checkForCredentials",method=RequestMethod.POST)
	@ResponseBody
	public boolean checkForCredentials(@RequestParam("username") String username,@RequestParam("password") String password) {
		
		boolean isMatched = false;		
		String errorMessage = null;
		
		try {
			String passwordSha256Hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
			isMatched = userService.checkCredentials(username, passwordSha256Hex);
		}
		catch (Exception ex) {
			System.out.println("Exception :" + ex);
			return false;
		}
		finally {		
			if(isMatched) {
				return true;
			}
			else {
				return false;
			}			
		}	
	}
	
	
}
