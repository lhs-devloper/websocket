package com.lhs.dao.interfaceFolder;

import com.lhs.dto.Room;

public interface UpdateDAO<T> {
	default int insert(T object) {
		return 0;
	}
	default int update(T object) {
		return 0;
	};
	default int delete(T object) {
		return 0;
	}
	default int insert(Room room, int userId) {
		return 0;
	}
	default int delete(int roomId) {
		return 0;
	}
}
