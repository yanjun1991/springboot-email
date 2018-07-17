package com.example.demo.controller;

import java.util.Map;

import com.example.demo.entity.User;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/saveUSer")
	@ResponseBody
	public String getUser(){
		User user = new User();
		user.setName("小明");
		userService.insert(user);
		return "success";
	}
	@RequestMapping(value = "/test", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json;charset=UTF-8")
	public User login(@RequestBody int id){
		User user = userService.getById(id);
		return user;
	}

}
