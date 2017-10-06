package com.myapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.myapp.beans.Book;
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
			List<Book> bookList = userService.getAssignedBooks(user);
			model.addAttribute("bookList",bookList);
			return "userHome";
		}
		return "error";
	}
	
	@ResponseBody
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public String updateBookCompletion(@RequestParam String bookName, Model model,HttpSession session)
	{
		User user = (User) session.getAttribute("user");
		int numRows = userService.updateBookCompletion(user,bookName);
		return (numRows == 1)?"true":"false";
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
