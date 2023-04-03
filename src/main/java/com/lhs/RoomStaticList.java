package com.lhs;

import java.util.ArrayList;

import com.lhs.controller.RoomController;
import com.lhs.dto.Room;

public class RoomStaticList {
	private static RoomController roomController = new RoomController();
	private static ArrayList<Room> roomList = roomController.requestByAllRoomList();
//	public RoomStaticList() {
//		roomController = new RoomController();
//		roomList = roomController.requestByAllRoomList();
//	};
	public static ArrayList<Room> getroomList(){
		return roomList;
	}
	public static void reloadRoomList() {
		roomList = roomController.requestByAllRoomList();
	}
}
