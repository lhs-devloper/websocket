package com.lhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.lhs.dao.interfaceFolder.SelectDAO;
import com.lhs.dao.interfaceFolder.UpdateDAO;
import com.lhs.dto.Room;
import com.lhs.dto.User;
import com.lhs.utils.DBHelper;

public class RoomDAO implements UpdateDAO<Room>, SelectDAO<Room> {
	private Connection conn;

	public RoomDAO() {
		this.conn = DBHelper.getInstance().getConnection();
	}
	@Override
	public Room select(int id) {
		Room room = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String findSQL = "select r.* "
				+ "from Room as r "
				+ "where r.id = ? ";
		try {
			pstmt = conn.prepareStatement(findSQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				room = new Room(
						rs.getInt("id"), 
						rs.getString("title"),
						rs.getInt("entry_limit"),
						rs.getInt("owner")
				);
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
		
		return room;
	}
	
	public int selectLastId() {
		int autoIncrement = 0;
		String findSQL = "SELECT MAX(id)+1 as id FROM room";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(findSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				autoIncrement = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
					pstmt.close();					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return autoIncrement;
	}
	
	@Override
	public ArrayList<Room> select(){
		ArrayList<Room> roomList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String findSQL = "select * from Room";
		try {
			pstmt = conn.prepareStatement(findSQL);
			rs = pstmt.executeQuery();
			roomList = new ArrayList<>();
			while(rs.next()){
				Room room = new Room(
						rs.getInt("id"), 
						rs.getString("title"),
						rs.getInt("entry_limit"),
						rs.getInt("owner")
				);
				roomList.add(room);
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
		
		return roomList;
	}
	
	@Override
	public int insert(Room room) {
		int resultCount = 0;
		PreparedStatement pstmt = null;
		String insertSQL = "insert into Room(title, entry_limit, owner)"
				+ "values(?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, room.getTitle());
			pstmt.setInt(2, room.getEntryLimit());
			pstmt.setInt(3, room.getOwnerId());
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
	public int delete(int roomId) {
		int resultCount = 0;
		PreparedStatement pstmt = null;
		String insertSQL = "delete from room where id=? ";
		try {
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, roomId);
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
