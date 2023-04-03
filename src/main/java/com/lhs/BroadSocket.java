package com.lhs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.lhs.dao.RoomDAO;
import com.lhs.dao.UserDAO;
import com.lhs.dto.Chat;
import com.lhs.dto.Room;
import com.lhs.dto.User;

@ServerEndpoint(value="/broadsocket", configurator=WebSocketConfigurator.class)
public class BroadSocket {
	// private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
	private static ArrayList<Room> roomList;
	private static HashMap<Integer, Room> roomUsers;
	private static Map<Session, HttpSession> sessionMap = new HashMap<Session, HttpSession>();
	private static Map<Session, Integer> roomMap = new HashMap<Session, Integer>();
	private static Map<String, Session> nicknameMap = new HashMap<String, Session>(); 
	private ChatController chatController = new ChatController();
	private UserDAO userDAO = new UserDAO();
	
	// browser에서 웹 소켓으로 접속하면 호출되는 함수
	public BroadSocket() {
		roomList = RoomStaticList.getroomList();
		System.out.println("BroadSocket Checked");
		if(roomUsers == null) {
			roomUsers = new HashMap<>();
			for(int i = 0; i < roomList.size(); i++) {	
				roomUsers.put(roomList.get(i).getId(), roomList.get(i));
			}
		}
	}
	
	@OnOpen
	public void handleOpen(Session userSession, EndpointConfig config) {
		HttpSession session = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		// httpsession & websocket mapping
		sessionMap.put(userSession, session);
		// usernicname & websocket mapping;
		User user = (User)session.getAttribute("user");
		nicknameMap.put(user.getNickname(),userSession);
		// sessionUsers.add(userSession);
	}

	// browser에서 웹 소켓을 통해 메시지가 오면 호출되는 함수
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
//		RoomStaticList.reloadRoomList();
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
				System.out.println("현재 방:" + userList);
				int limit = roomUsers.get(roomId).getEntryLimit();
				int nowRoomUserCount = userList.size();
				JSONObject newJson = new JSONObject();
				newJson.put("user", ((User)session.getAttribute("user")).getNickname());
				newJson.put("data", ((User)session.getAttribute("user")).getNickname()+ "님 께서 입장하셨습니다");
				newJson.put("system", "system");
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
					String target = String.valueOf(json.get("target"));
					JSONObject newJson = new JSONObject();
					newJson.put("user", ((User)session.getAttribute("user")).getNickname());
					
					if("target".equals(target)) {
						String targetUser = String.valueOf(json.get("targetUser"));
						System.out.println(targetUser);
						if(!targetUser.equals("")) {
							newJson.put("data", content);
							User user = userDAO.selectGetId(targetUser);
							Session targetSession = nicknameMap.get(user.getNickname());
							System.out.println(userSession);
							System.out.println(targetSession);
							newJson.put("dm", "dm");
							boolean isTrue = true;
							for(int i = 0; i < nowUserCount; i++) {
								if(roomUserList.get(i) == targetSession) {
									String msg = newJson.toJSONString();
									roomUserList.get(i).getBasicRemote().sendText(msg);
									isTrue = false;
									break;
								}
							}
						}else {
							newJson.put("data", "잘못된 귓속말 사용법입니다 좌측상단을 통해 확인해주세요");
							newJson.put("system", "system");
							String msg = newJson.toJSONString();
							userSession.getBasicRemote().sendText(msg);
						}
						// messaging
						// roomUserList.get(0).getBasicRemote().sendText(msg);
					}else {
						newJson.put("data", content);
						String msg = newJson.toJSONString();
						for(int i = 0; i < nowUserCount; i++) {
							if( roomUserList.get(i) == userSession ) {
								continue;
							}
							roomUserList.get(i).getBasicRemote().sendText(msg);
						}
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
			JSONObject newJson = new JSONObject();
			newJson.put("user", ((User)session.getAttribute("user")).getNickname());
			newJson.put("system", "system");
			newJson.put("data", ((User)session.getAttribute("user")).getNickname()+"님 께서 퇴장하셨습니다");
			String msg = newJson.toJSONString();
			for(Session user: roomUsers.get(roomId).getUserList()) {					
				user.getBasicRemote().sendText(msg);
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
