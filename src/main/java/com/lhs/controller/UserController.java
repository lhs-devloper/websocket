package com.lhs.controller;

import com.lhs.dto.User;
import com.lhs.service.UserService;

public class UserController {
	
	private UserService userService;
	public UserController() {
		userService = new UserService();
	}
	
	public boolean requestBySignUp(User userDTO) {
		boolean isTrue = false;
		isTrue = userService.signUpService(userDTO);
		return isTrue;
	}
	
	// 로그인
	public boolean requestBySignIn(String id, String pw) {
		boolean isTrue = false;
		isTrue = userService.loginService(id, pw);
		return isTrue;
	}
	
	public User requestUserInfo(String id) {
		User user = null;
		user = userService.findInfo(id);
		return user;
	}

}
