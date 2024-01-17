package com.todo.app.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.todo.app.dto.LoginRequest;
import com.todo.app.dto.LoginResponse;
import com.todo.app.dto.UserDTO;
import com.todo.app.entity.User;
import com.todo.app.mapper.UserMapper;
import com.todo.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		LoginResponse response = new LoginResponse();
		try {
			Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				String password = loginRequest.getPassword();
				if (password.equals(user.getPassword())) {
					UserDTO userDTO2 = userMapper.toUserDTO(user);
					log.info(messageSource.getMessage("LOGIN_SUCCESSFUL", null, Locale.ENGLISH));
					response.setStatusCode(200);
					response.setStatusMessage(messageSource.getMessage("LOGIN_SUCCESSFUL", null, Locale.ENGLISH));
					response.setUser(user);
				} else {
					log.info(messageSource.getMessage("PASSWORD_INVALID", null, Locale.ENGLISH));
					response.setStatusCode(400);
					response.setStatusMessage(messageSource.getMessage("PASSWORD_INVALID", null, Locale.ENGLISH));
				}
			} else {
				log.info(messageSource.getMessage("USER_NOT_FOUND", null, Locale.ENGLISH));
				response.setStatusCode(400);
				response.setStatusMessage(messageSource.getMessage("USER_NOT_FOUND", null, Locale.ENGLISH));
			}
		} catch (Exception e) {
			log.info(messageSource.getMessage("LOGIN_EXCEPTION", null, Locale.ENGLISH));
			response.setStatusCode(500);
			response.setStatusMessage(messageSource.getMessage("LOGIN_EXCEPTION", null, Locale.ENGLISH));
		}

		return response;

	}
	
	
}
