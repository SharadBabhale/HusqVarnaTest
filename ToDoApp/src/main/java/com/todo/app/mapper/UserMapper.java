package com.todo.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.todo.app.dto.UserDTO;
import com.todo.app.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	public UserDTO toUserDTO(User user);
	
	public User toUser(UserDTO userDTO);
	
	public List<User> toUsers(List<UserDTO> userDTOs);
	
	public List<UserDTO> toUserDTOs(List<User> users);

}