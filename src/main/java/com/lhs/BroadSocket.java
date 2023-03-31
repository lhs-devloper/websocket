package com.lhs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.lhs.controller.ChatController;
import com.lhs.controller.RoomController;
import com.lhs.dto.Chat;
import com.lhs.dto.Room;
import com.lhs.dto.User;
import com.lhs.sevlets.LoginHandler;

@ServerEndpoint(value="/broadsocket", configurator=WebSocketConfigurator.class)
public class BroadSocket {
	// private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
	private static ArrayList<Room> roomList = LoginHandler.getRoomList();
	private static HashMap<Integer, Room> roomUsers;
	private static Map<Session, HttpSession> sessionMap = new HashMap<Session, HttpSession>();
	private static Map<Session, Integer> roomMap = new HashMap<Session, Integer>();
	private ChatController chatController = new ChatController();
	
	// browser에서 웹 소켓으로 접속하면 호출되는 함수
	public BroadSocket() {
		System.out.println("BroadSocket Checked");
		if(roomUsers == null) {
			roomUsers = new HashMap<>();
			for(int i = 0; i < roomList.size(); i++) {	
				System.out.println(roomList.get(i).getId());
				roomUsers.put(roomList.get(i).getId(), roomList.get(i));
			}
		}
	}
	
	@OnOpen
	public void handleOpen(Session userSession, EndpointConfig config) {
		HttpSession session = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		sessionMap.put(userSession, session);
		// sessionUsers.add(userSession);
	}

	// browser에서 웹 소켓을 통해 메시지가 오면 호출되는 함수
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
	/*
		if(roomUsers.get(1) == null) {
			Room room = new Room(1, "test", 10);
			roomUsers.put(1, room);
		}
	*/
		// =====================================================
		parseProtocol(message, userSession);
		
	/*
		String serverMessage = "지금 서버작업 중이라 메시지 전달 기능 X ";
		userSession.getBasicRemote().sendText(serverMessage);
	*/
	}

	// 운영자 client가 유저에게 메시지를 보내는 함수
	public static void sendMessage(String message) {

	}
	// JSON 처리
	public void parseProtocol(String message, Session userSession) throws IOException{
		try {			
			JSONParser parser = new JSONParser();
			Object JsonMessage = parser.parse(message);
			JSONObject json = (JSONObject) JsonMessage;
			String order = String.valueOf(json.get("order"));
			int roomId = Integer.valueOf(String.valueOf(json.get("roomId")));
			if("enter".equals(order)) {
				HttpSession session = sessionMap.get(userSession);
				ArrayList<Session> userList = roomUsers.get(roomId).getUserList();
				int limit = roomUsers.get(roomId).getEntryLimit();
				int nowRoomUserCount = userList.size();
				JSONObject newJson = new JSONObject();
				newJson.put("user", ((User)session.getAttribute("user")).getNickname());
				newJson.put("data", ((User)session.getAttribute("user")).getNickname()+ "님 께서 입장하십니다");
				String msg = newJson.toJSONString();
				if(nowRoomUserCount < limit) {
					roomMap.put(userSession, roomId);
					userList.add(userSession);
					roomUsers.get(roomId).setUserList(userList);
					for(int i = 0; i < nowRoomUserCount; i++) {
//						userList.get(i).getBasicRemote().sendText(((User)session.getAttribute("user")).getNickname()+ "님 께서 입장하십니다");
						userList.get(i).getBasicRemote().sendText(msg);
					}
					System.out.println(roomUsers.get(roomId).getUserList());					
				} else {
					userSession.getBasicRemote().sendText("인원 입장X");
				}
			}
			if("message".equals(order)) {
				
				String content = String.valueOf(json.get("content"));
				ArrayList<Session> roomUserList = roomUsers.get(roomId).getUserList();
				HttpSession session = sessionMap.get(userSession);
				int nowUserCount = roomUserList.size();
				Chat chat = new Chat.ChatBuilder()
						.setContent(content)
						.setRoom(roomId)
						.setUser((User) session.getAttribute("user"))
						.build();
				if(chatController.messaging(chat)) {
					JSONObject newJson = new JSONObject();
					newJson.put("user", ((User)session.getAttribute("user")).getNickname());
					newJson.put("data", content);
					String msg = newJson.toJSONString();
					for(int i = 0; i < nowUserCount; i++) {
						if(roomUserList.get(i) == userSession) {
							continue;
						}
						roomUserList.get(i).getBasicRemote().sendText(msg);
					}
				}
				
			}
//			if("create".equals(order)) {
//				// 수정해야함
//				HttpSession session = sessionMap.get(userSession);
//				int EntryLimit = Integer.valueOf(String.valueOf(json.get("roomId")));
//				String title = String.valueOf(json.get("roomId"));
//				User user = (User) session.getAttribute("user");
//				Room room = new Room(
//						roomId, 
//						title, 
//						EntryLimit, 
//						user.getId()
//				);
//				
//				roomUsers.put(roomId, room);
//			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	// WebSocket이 종료가 되면, 종료 버튼이 없기 때문에 유저 브라우저가 닫히면 발생한다.
	@OnClose
	public void handleClose(Session userSession) throws IOException {
		if(roomMap.get(userSession) != null) {
			HttpSession session = sessionMap.get(userSession);
			int roomId = roomMap.get(userSession);
			roomUsers.get(roomId).getUserList().remove(userSession);
			for(Session user: roomUsers.get(roomId).getUserList()) {					
				user.getBasicRemote().sendText(((User)session.getAttribute("user")).getNickname()+"님 께서 퇴장하셨습니다");
			}

			roomMap.remove(userSession);
		}
	}
	
	public static void BroadReload() {
		for(int i = 0; i < roomList.size(); i++) {			
			roomUsers.put(roomList.get(i).getId(), roomList.get(i));
		}
	}
	
	public static HashMap<Integer, Room> getData(){
		return roomUsers;
	}
}
