package com.lhs.dao.interfaceFolder;

import java.util.ArrayList;

public interface SelectDAO<T> {
	default int selectLastId() {
		return 0;
	}
	default ArrayList<T> select(){
		return null;
	}
	
	default T select(int id){
		return null;
	}
	default T select(String id){
		return null;
	}
}
