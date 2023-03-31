package com.lhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lhs.dao.interfaceFolder.SelectDAO;
import com.lhs.dao.interfaceFolder.UpdateDAO;
import com.lhs.dto.Room;
import com.lhs.dto.User;
import com.lhs.utils.DBHelper;

public class UserDAO implements UpdateDAO<User>, SelectDAO<User> {
	private Connection conn;

	public UserDAO() {
		this.conn = DBHelper.getInstance().getConnection();
	}

	
	@Override
	public User select(String id) {
		User user = null;
		String findSQL = "select * from User where user_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(findSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				user = new User.UserBuilder()
						.setId(rs.getInt("id"))
						.setNickname(rs.getString("nickname"))
						.setPassword(rs.getString("password"))
						.setUserId(rs.getString("user_id"))
						.build();
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		return user;
	}
	
	@Override
	public int insert(User user) {
		int resultCount = 0;
		String insertSQL = "insert into User(user_id, password, nickname) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getNickname());
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

	@Override
	public int update(User user) {
		int resultCount = 0;
		String insertSQL = "update User set password = ?, nickname = ? "
				+ "where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getNickname());
			resultCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultCount;
	}

}
