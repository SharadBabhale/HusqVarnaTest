package com.todo.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public Optional<User> findByEmail(String email);

}
