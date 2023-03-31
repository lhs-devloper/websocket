package com.lhs.sevlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhs.BroadSocket;
import com.lhs.controller.RoomController;
import com.lhs.controller.UserController;
import com.lhs.dto.Room;
import com.lhs.dto.User;

@WebServlet("/login")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController userController;
	private RoomController roomController;
	private static ArrayList<Room> roomList;
	@Override
	public void init() throws ServletException {
		userController = new UserController();
		roomController = new RoomController();
		roomList = roomController.requestByAllRoomList();
		new BroadSocket();
		new roomHandler();
		System.out.println(roomList);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(userController.requestBySignIn(id, pw)) {
			User user = userController.requestUserInfo(id);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("rooms", roomList);
			response.sendRedirect("index.jsp");
		} else {
			request.setAttribute("error", "error");
			request.getRequestDispatcher("/login.jsp").include(request, response);
		}
	}
	
	public static ArrayList<Room> getRoomList() {
		return roomList;
	}
}
