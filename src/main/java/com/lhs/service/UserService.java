package com.lhs.service;

import com.lhs.dao.UserDAO;
import com.lhs.dto.User;

public class UserService {
	private UserDAO userDAO;

	public UserService() {
		userDAO = new UserDAO();
	}

	public boolean loginService(String id, String password) {
		boolean isSuccess = false;
		User loginUser = userDAO.select(id);
		if(loginUser != null) {
			
			if(id.equals(loginUser.getUserId())){
				
				if(password.equals(loginUser.getPassword())) {
					isSuccess = true;
				}
				
			}
			
		}
		return isSuccess;
	}
	
	public boolean signUpService(User user) {
		boolean isSuccess = false;
		isSuccess = userDAO.insert(user) == 0 ? false : true;
		return isSuccess;
	}
	
	public User findInfo(String id) {
		User user = null;
		user = userDAO.select(id);
		return user;
	}
}
