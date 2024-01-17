package com.todo.app.entity;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.todo.app.enums.CustomerStatus;
import com.todo.app.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private CustomerStatus customerStatus;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Roles roles;
	
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="password")
	private String password;
	
	@Column(name="address")
	private String address;
	
	//private String token;
	
	
}
