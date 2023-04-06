package com.lhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lhs.dao.interfaceFolder.UpdateDAO;
import com.lhs.dto.Chat;
import com.lhs.dto.Room;
import com.lhs.utils.DBHelper;

public class ChatDAO implements UpdateDAO<Chat>{
	private Connection conn;
	public ChatDAO() {
		this.conn = DBHelper.getInstance().getConnection();
	}
	
	@Override
	public int insert(Chat chat) {
		int resultCount = 0;
		PreparedStatement pstmt = null;
		String insertSQL = "insert into Chat(room_id, user_id, content)"
				+ "values(?, ?, ?);";
		try {
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, chat.getRoom());
			pstmt.setInt(2, chat.getUser().getId());
			pstmt.setString(3, chat.getContent());
			System.out.println(pstmt);
			resultCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultCount;
	}

	
	
}
