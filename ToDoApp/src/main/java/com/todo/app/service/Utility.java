package com.todo.app.service;

import java.sql.Timestamp;

public class Utility {
	
	public static boolean isNullOrEmpty(Timestamp dueDate) {
		return dueDate==null;
	}

	public static boolean isNullOrEmpty(String value) {
		return value==null || value.trim().equals("");
	}

	
}
