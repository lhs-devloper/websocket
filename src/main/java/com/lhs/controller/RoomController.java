package com.lhs.controller;

import java.util.ArrayList;

import com.lhs.dto.Room;
import com.lhs.service.RoomService;

public class RoomController {
	private RoomService roomService;
	public RoomController() {
		roomService = new RoomService();
	}
	
	public ArrayList<Room> requestByAllRoomList() {
		ArrayList<Room> roomList = null;
		roomList = roomService.allRooms();
		return roomList;
	}
	
	public boolean requestByCreateRoom(Room room) {
		boolean isSuccess = false;
		isSuccess = roomService.createRoom(room);
		return isSuccess;
	}
	
	public boolean requestBySearchRoom(int id) {
		Room room = null;
		room = roomService.selectedById(id);
		if(room == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public Room requestByRoom(int id) {
		Room room = null;
		room = roomService.selectedById(id);
		return room;
	}
	
	public int requestByAutoIncrement() {
		int autoIncrement = 0;
		autoIncrement = roomService.findAutoIncrement();
		return autoIncrement;
	}
	
	public boolean requestByDeleteRoom(int id,	int roomId) {
		boolean isSuccess = false;
		isSuccess = roomService.deleteRoom(id, roomId);
		return isSuccess;
	}
}
