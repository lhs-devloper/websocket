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

import com.lhs.BroadSocket;
import com.lhs.RoomStaticList;
import com.lhs.controller.RoomController;
import com.lhs.dto.Room;
import com.lhs.dto.User;

/**
 * Servlet implementation class roomHandler
 */
@WebServlet("/room")
public class roomHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomController roomController;
    
    @Override
    public void init() throws ServletException {
    	roomController = new RoomController();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BroadSocket.BroadReload();
		PrintWriter out = response.getWriter();
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
		
		if(request.getSession().getAttribute("user") == null){
			response.sendRedirect("login");
		}
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
				System.out.println(id);
				Room roomInfo = roomUsers.get(Integer.valueOf(id));
				System.out.println(RoomStaticList.getroomList());
				System.out.println("나를 제외한 현재 인원:" +RoomStaticList.getroomList().get(index).getUserList().size());
				System.out.println("방 제한:" +RoomStaticList.getroomList().get(index).getEntryLimit());
				System.out.println("방 개수: "+RoomStaticList.getroomList().size());
				int now = RoomStaticList.getroomList().get(index).getUserList().size();
				int limit = RoomStaticList.getroomList().get(index).getEntryLimit();
				if(limit <= now) {
					System.out.println("??");
					out.print("<script>alert('입장인원 제한 초과'); location.href='"+"index.jsp"+"';</script>");
				}
				
			}
			if(RoomStaticList.getroomList().get(index).getPassword() != null) {
				response.sendRedirect("password.jsp?id="+id);
			}else {
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				request.setCharacterEncoding("UTF-8");
				
				response.sendRedirect("room.jsp?id="+id);
			}
//			RequestDispatcher dispather = request.getRequestDispatcher("/room.jsp");
			
//			dispather.forward(request, response);
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
		Room room = null;
		String title = request.getParameter("title");
		User owner = (User)request.getSession().getAttribute("user");
		System.out.println("owner: " + owner.getId());
		System.out.println("Nickname: " +owner.getNickname());
		String check = request.getParameter("check");
		if(check != null) {			
			String pw = request.getParameter("pw");
			room = new Room(title, limit, owner.getId(), pw);
		} else {
			room = new Room(title, limit, owner.getId(), null);
		}
		int roomLastId = roomController.requestByAutoIncrement();
		RoomStaticList.getroomList().add(room);
		if(roomController.requestByCreateRoom(room)) {
			RoomStaticList.reloadRoomList();
			response.sendRedirect("index.jsp");			
		} else {
			response.sendRedirect("500.jsp");
		}
	}
}
