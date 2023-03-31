package com.lhs.sevlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lhs.BroadSocket;
import com.lhs.controller.RoomController;
import com.lhs.dto.Room;
import com.lhs.dto.User;

@WebServlet("/delete")
public class deleteHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomController roomController;
	private static ArrayList<Room> roomList;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	@Override
	public void init() throws ServletException {
		roomController = new RoomController();
		roomList = LoginHandler.getRoomList();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String target = request.getParameter("target");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println(roomList);
		if("room".equals(target)) {
			String id = request.getParameter("id");
			int index = Integer.valueOf(request.getParameter("index"));
			System.out.println(index);
			if(roomController.requestByDeleteRoom(user.getId(), Integer.valueOf(id))) {
				roomList.remove(index);
				System.out.println(roomList);
				response.sendRedirect("index.jsp");				
			}else {
				out.print("<script>alert('삭제 권한 X'); location.href='"+"index.jsp"+"';</script>");
			}
		}
		if("user".equals(target)) {
			request.getSession().invalidate();
			response.sendRedirect("index.jsp");
		}

	}

}
