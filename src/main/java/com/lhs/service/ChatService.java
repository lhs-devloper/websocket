package com.lhs.service;

import com.lhs.dao.ChatDAO;
import com.lhs.dto.Chat;

public class ChatService {
	private ChatDAO chatDAO;
	public ChatService() {
		chatDAO = new ChatDAO();
	}
	
	public boolean messaging(Chat chatDTO) {
		boolean isTrue = false;
		int result = chatDAO.insert(chatDTO);
		isTrue = result == 0 ? false: true;
		return isTrue;
	}
}
