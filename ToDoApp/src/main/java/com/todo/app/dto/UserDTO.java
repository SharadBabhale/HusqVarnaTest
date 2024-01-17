package com.todo.app.dto;

import java.sql.Date;

import com.todo.app.enums.CustomerStatus;
import com.todo.app.enums.Roles;

import lombok.Data;

@Data

public class UserDTO {
	
	private int userId;
	
	private CustomerStatus customerStatus;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
		
	private Roles roles;
	
	private Date dateOfBirth;
	
	private String token;
	
	private String address;
	
}
