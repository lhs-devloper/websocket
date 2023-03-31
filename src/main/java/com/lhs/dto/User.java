package com.lhs.dto;

import com.lhs.dto.Chat.ChatBuilder;

public class User {
	private int id;
	private String userId;
	private String password;
	private String nickname;
	
	public static class UserBuilder {
		private int id;
		private String userId;
		private String password;
		private String nickname;
		
		public UserBuilder setId(int id) {
			this.id = id;
			return this;
		}
		
		public UserBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public UserBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserBuilder setNickname(String nickname) {
			this.nickname = nickname;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
	private User(UserBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.password = builder.password;
        this.nickname = builder.nickname;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
