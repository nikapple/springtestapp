package com.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.myapp.beans.User;
import com.myapp.services.UserService;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) {
		if (session.isNew()) {
			System.out.println(session.getId());
			User user = (User) session.getAttribute("user");
			if (user != null) {
				return "redirect:userHome/" + user.getUsername();
			}
		}
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLogin(@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult, Model userModel) {
		if (bindingResult.hasFieldErrors("email")
				|| bindingResult.hasFieldErrors("password")) {
			return "login";
		} else if (!userService.validateUser(user)) {
			return "login";
		}
		user = userService.getUserInfo(user);
		userModel.addAttribute("user", user);
		return "redirect:userHome/" + user.getUsername();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		} else if (userService.checkUserExists(user)) {
			return "register";
		}
		userService.insertUser(user);
		return "registerSuccess";
	}

	@RequestMapping(value = "/userHome/{username}", method = RequestMethod.GET)
	public String userHome(@PathVariable String username, Model model,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user.getUsername().equals(username)) {
			return "userHome";
		}
		return "error";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if (!session.isNew()) {
			session.removeAttribute("user");
			session.invalidate();
			System.out.println(session.getId());
		}
		return "redirect:login";
	}
}
