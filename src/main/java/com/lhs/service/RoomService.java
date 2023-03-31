package com.lhs.service;

import java.util.ArrayList;

import com.lhs.dao.ChatDAO;
import com.lhs.dao.RoomDAO;
import com.lhs.dto.Chat;
import com.lhs.dto.Room;

public class RoomService {
	private RoomDAO roomDAO;
	public RoomService() {
		roomDAO = new RoomDAO();
	}
	public ArrayList<Room> allRooms(){
		ArrayList<Room> roomList = null;
		roomList = roomDAO.select();
		return roomList;
	}
	
	public Room selectedById(int id) {
		Room room = null;
		room = roomDAO.select(id);
		return room;
	}
	
	public boolean createRoom(Room room) {
		boolean isSuccess = false;
		
		isSuccess = roomDAO.insert(room) == 0 ? false : true;
		
		return isSuccess;
		
	}
	
	public int findAutoIncrement() {
		int autoIncrement = 0;
		autoIncrement = roomDAO.selectLastId();
		return autoIncrement;
	}
	
	public boolean deleteRoom(int id, int roomId) {
		boolean isSuccess = false;
		if(id == roomDAO.select(roomId).getOwnerId()) {
			isSuccess = roomDAO.delete(roomId) == 0 ? false : true;
		}
		return isSuccess;
	}
}
