package com.todo.app.service;

import org.springframework.stereotype.Service;

import com.todo.app.dto.LoginRequest;
import com.todo.app.dto.LoginResponse;

@Service
public interface UserService {
	public LoginResponse login(LoginRequest loginRequest);
	
	
}
