package com.lhs.dao;

public class TestDAO {
	public static void main(String[] args) {
		RoomDAO dao = new RoomDAO();
		System.out.println(dao.selectLastId());
	}
}
