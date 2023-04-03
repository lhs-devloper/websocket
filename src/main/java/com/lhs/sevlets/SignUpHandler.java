package com.lhs.sevlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhs.controller.UserController;
import com.lhs.dto.User;

@WebServlet("/signUp")
public class SignUpHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController userController;
	public SignUpHandler() {
		userController = new UserController();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		
		User user = new User.UserBuilder()
				.setPassword(pw)
				.setNickname(nickname)
				.setUserId(id)
				.build();
		
		if(userController.requestBySignUp(user)) {
			if(request.getAttribute("error") != null) {
				request.removeAttribute("error");
			}
			response.sendRedirect("login");
		} else {
			request.setAttribute("error", "signUpError");
			request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}
	}

}
