package com.todo.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.dto.LoginRequest;
import com.todo.app.dto.LoginResponse;
import com.todo.app.entity.User;
import com.todo.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@PostMapping(value = "login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userDTO, HttpServletRequest httpServletRequest){
		log.info("Log in user");
		HttpSession session = httpServletRequest.getSession();
		LoginResponse response = userService.login(userDTO);
		if(response.getStatusCode()==200) {
			User userDTORes = response.getUser();
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority(userDTORes.getRoles().name());
			authorities.add(authority);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), null, authorities);
			usernamePasswordAuthenticationToken.setDetails(userDTORes);
			session.setAttribute("SESSION_USER", userDTORes);
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			response.setToken(session.getId());
			response.setUser(userDTORes);
		}
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
}
