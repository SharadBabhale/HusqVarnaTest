package com.todo.app.dto;
import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {
	 
	private String email;
	
	private String password;
	
}
