package com.lhs.dto;

public class Chat {
	private int roomId;
	private User user;
	private String content;
	
	public static class ChatBuilder {
		private int roomId;
		private User user;
		private String content;
		
		public ChatBuilder setRoom(int roomId) {
			this.roomId = roomId;
			return this;
		}
		
		public ChatBuilder setUser(User user) {
			this.user = user;
			return this;
		}
		public ChatBuilder setContent(String content) {
			this.content = content;
			return this;
		}
		public Chat build() {
			return new Chat(this);
		}
	}
	private Chat(ChatBuilder builder) {
        this.user = builder.user;
        this.roomId = builder.roomId;
        this.content = builder.content;
    }
	public int getRoom() {
		return roomId;
	}
	public void setRoom(int roomId) {
		this.roomId = roomId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
