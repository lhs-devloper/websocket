package com.lhs;

import java.util.ArrayList;

import com.lhs.controller.RoomController;
import com.lhs.dto.Room;

public class RoomList {
	private RoomController roomController;
	private static ArrayList<Room> roomList;
	public RoomList() {
		roomList = roomController.requestByAllRoomList();
	};	
}
