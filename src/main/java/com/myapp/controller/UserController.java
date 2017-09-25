package com.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myapp.beans.User;
import com.myapp.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model){
		model.addAttribute("user",new User());
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String processLogin(@ModelAttribute("user") User user ,Model userModel){
		userService.validateUser(user);
		userModel.addAttribute("username",user.getUsername());
		return "loginSuccess";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model){
		model.addAttribute("user",new User());
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String processRegistration(@ModelAttribute("user") User user,Model userModel){
		userService.insertUser(user);
		userModel.addAttribute("username",user.getUsername());
		return "registerSuccess";
	}
}
