package com.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatapp.model.UserProfile;
import com.chatapp.service.UserService;


@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("user/list");
		model.addObject("listUser",userService.listUser());
		return model;
	}

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView model = new ModelAndView("user/form");
		model.addObject("userform", new UserProfile());
		return model;
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") String id) {
		ModelAndView model = new ModelAndView("user/form");
		model.addObject("userform", userService.findUserById(id));
		return model;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute("userform") UserProfile user) {		
		if(user.getId() != null && !user.getId().trim().equals("")){
			//update
			userService.update(user);
		}
		else {
			userService.add(user);
		}
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@ModelAttribute("id") String id) {
		UserProfile user = userService.findUserById(id);
		System.out.println(" going to delete user = " + user.getId() + "  " + user.getName());
		userService.delete(user);
		return "redirect:/user/list";
	}
	
}
