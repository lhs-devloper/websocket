package com.lhs.dto;

import java.util.ArrayList;

import javax.websocket.Session;

import com.lhs.dto.Chat.ChatBuilder;

public class Room {
	private int id;
	private String title;
	private int entryLimit;
	private int onwerId;
	ArrayList<Session> userList = new ArrayList<Session>();
	
	public Room(int id, String title, int entryLimit, int onwerId) {
		this.id = id;
		this.title = title;
		this.entryLimit = entryLimit;
		this.onwerId = onwerId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getEntryLimit() {
		return entryLimit;
	}
	public void setEntryLimit(int entryLimit) {
		this.entryLimit = entryLimit;
	}
	public ArrayList<Session> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<Session> userList) {
		this.userList = userList;
	}
	public int getOwnerId() {
		return this.onwerId;
	}
	public void setOwnerId(int ownerId) {
		this.onwerId = ownerId;
	}
}
