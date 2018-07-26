package com.example.demo.controller;

import com.example.demo.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.example.demo.service.UserService;

import java.util.List;

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
	@RequestMapping("/list")
	public String getList(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "totalPages", defaultValue = "3") Integer totalPages){
		List<User> userList = userService.getList(pageNum,totalPages,"id DESC");
		PageInfo page = new PageInfo(userList);
		model.addAttribute("page",page);

		return "index";

	}

}
