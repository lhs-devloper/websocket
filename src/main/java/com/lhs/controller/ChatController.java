package com.lhs.controller;

import com.lhs.dto.Chat;
import com.lhs.service.ChatService;

public class ChatController {
	private ChatService service;

	public ChatController() {
		this.service = new ChatService();
	}
	
	public boolean messaging(Chat chatDTO) {
		boolean result = false;
		result = service.messaging(chatDTO);
		System.out.println(result);
		return result;
	}
}
