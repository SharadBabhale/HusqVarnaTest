package com.todo.app.dto;

import com.todo.app.entity.User;

import lombok.Data;

@Data
public class LoginResponse {

	private int statusCode;

	private String statusMessage;
	
	private User user;
	
	private String token;
}
