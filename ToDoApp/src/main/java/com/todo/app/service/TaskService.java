package com.todo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.app.dto.PaginatedResponse;
import com.todo.app.dto.ServerResponse;
import com.todo.app.dto.TaskDTO;
import com.todo.app.enums.TaskStatus;

@Service
public interface TaskService {
	public TaskDTO saveTask(TaskDTO taskDTO);

	public PaginatedResponse getTasks(Integer pageNumber, Integer pageSize);
	
	public ServerResponse<TaskDTO> getTask(int taskId);
	
	public ServerResponse<String> deleteTask(int taskId);
	
	public ServerResponse updateTask(TaskDTO taskDTO, int taskId);

	public PaginatedResponse getTaskByStatus(TaskStatus status,Integer pageNumber, Integer pageSize);

}
