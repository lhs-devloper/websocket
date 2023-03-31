package com.lhs.sevlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
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

/**
 * Servlet implementation class roomHandler
 */
@WebServlet("/room")
public class roomHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayList<Room> roomList;
	private RoomController roomController;
    
    @Override
    public void init() throws ServletException {
    	roomController = new RoomController();
    	roomList = LoginHandler.getRoomList();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BroadSocket.BroadReload();
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset='utf-8'");
		response.setCharacterEncoding("UTF-8");
		if(request.getSession().getAttribute("user") == null){
			response.sendRedirect("login");
		}
		roomList = LoginHandler.getRoomList();
		String id = request.getParameter("id");
//		int parseId = Integer.parseInt(id);
//		if(parseId <= 30) {
//			out.print("<script>alert('입장인원 제한 초과'); location.href='"+"roomCreate.jsp"+"';</script>");
//		}
		System.out.println(roomController.requestBySearchRoom(Integer.valueOf(id)));
		if(roomController.requestBySearchRoom(Integer.valueOf(id))) {
			int index = Integer.valueOf(request.getParameter("index"));
			HashMap<Integer, Room> roomUsers = BroadSocket.getData();
			if(roomUsers != null) {
				Room roomInfo = roomUsers.get(Integer.valueOf(id));
				System.out.println(roomList);
				System.out.println("나를 제외한 현재 인원:" +roomInfo.getUserList().size());
				System.out.println("방 제한:" +roomList.get(index).getEntryLimit());
				int now = roomInfo.getUserList().size();
				int limit = roomList.get(index).getEntryLimit();
				if(limit <= now) {
					System.out.println("??");
					out.print("<script>alert('입장인원 제한 초과'); location.href='"+"index.jsp"+"';</script>");
				}
			}
			
			request.setAttribute("roomId", id);
			request.getRequestDispatcher("/room.jsp").include(request, response);
		} else {
			response.sendRedirect("index.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int limit = Integer.valueOf(request.getParameter("limit"));
		if(limit < 2) {
			response.sendRedirect("400.jsp");
		}
		String title = request.getParameter("title");
		User owner = (User)request.getSession().getAttribute("user");
		int roomLastId = roomController.requestByAutoIncrement();
		System.out.println(roomLastId);
		Room room = new Room(roomLastId, title, limit, owner.getId());
		roomList.add(room);
		if(roomController.requestByCreateRoom(room)) {
			response.sendRedirect("index.jsp");			
		} else {
			response.sendRedirect("500.jsp");
		}
	}
}
