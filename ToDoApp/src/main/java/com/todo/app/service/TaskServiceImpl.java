package com.todo.app.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.todo.app.dto.PaginatedResponse;
import com.todo.app.dto.ServerResponse;
import com.todo.app.dto.TaskDTO;
import com.todo.app.entity.Task;
import com.todo.app.enums.Priority;
import com.todo.app.enums.TaskStatus;
import com.todo.app.exception.TaskException;
import com.todo.app.mapper.TaskMapper;
import com.todo.app.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Override
	public TaskDTO saveTask(TaskDTO taskDTO) {
		validateTaskDTO(taskDTO);
		if(Utility.isNullOrEmpty(taskDTO.getPriority()))
			taskDTO.setPriority(Priority.LOW.name());
		if(Utility.isNullOrEmpty(taskDTO.getStatus()))
			taskDTO.setStatus(TaskStatus.TODO.name());
		Task task = taskMapper.toTask(taskDTO);
		task = taskRepository.save(task);
		log.info("Task Details saved successfully");

		taskDTO = taskMapper.toTaskDTO(task);
		return taskDTO;
	}
	
	void validateTaskDTO(TaskDTO taskDTO){
		if(Utility.isNullOrEmpty(taskDTO.getTitle()))
			throw new TaskException("Task title required");
		validateEnum(taskDTO);
	}
	
	void validateEnum(TaskDTO taskDTO){
		if(!Utility.isNullOrEmpty(taskDTO.getPriority())) {
			try {
				Priority.valueOf(taskDTO.getPriority());
			}catch (Exception e) {
				throw new TaskException("Priority Contains unexpected data");
			}
		}
		if(!Utility.isNullOrEmpty(taskDTO.getStatus())) {
			try {
				TaskStatus.valueOf(taskDTO.getStatus());
			}catch (Exception e) {
				throw new TaskException("Status Contains unexpected data");
			}
		}
	}
	
	@Override
	public PaginatedResponse getTasks(Integer pageNumber, Integer pageSize) {
		if(pageNumber==null)
			pageNumber = 0;
		if(pageSize==null)
			pageSize = 5;
		Page<Task> tasks = taskRepository.findAll(PageRequest.of(pageNumber, pageSize));
		List<TaskDTO> tasksList = taskMapper.toTaskDTOs(tasks.getContent());
		PaginatedResponse response = PaginatedResponse.builder().
				body(tasksList).
				pageCount(tasks.getTotalPages()).
				pageNumber(tasks.getNumber())
				.isLast(tasks.isLast()).
				count(tasks.getNumberOfElements())
				.pageSize(tasks.getSize()).build();
		return response;
	}

	@Override
	public ServerResponse<TaskDTO> getTask(int taskId) {
		ServerResponse response = new ServerResponse<>();
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if(taskOptional.isPresent()) {
			Task task = taskOptional.get();
			response.setResponse(taskMapper.toTaskDTO(task));
			response.setStatusCode(200);
		}else {
			throw new TaskException("Invalid Task ID");
		}
		return response;
	}

	@Override
	public ServerResponse<String> deleteTask(int taskId) {
		ServerResponse response = new ServerResponse();
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if(taskOptional.isPresent()) {
			taskRepository.deleteById(taskId);
			response.setStatusCode(200);
			response.setStatusMessage("Task Deleted: "+taskId);
		}else {
			throw new TaskException("Invalid Task ID: "+taskId);
		}
		return response;
	}

	@Override
	public ServerResponse updateTask(TaskDTO taskDTO, int taskId) {
		validateEnum(taskDTO);
		ServerResponse response = new ServerResponse();
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if(taskOptional.isPresent()) {
			Task task = taskOptional.get();
			if(!Utility.isNullOrEmpty(taskDTO.getDescription()))
				task.setDescription(taskDTO.getDescription());
			
			if(!Utility.isNullOrEmpty(taskDTO.getPriority()))
				task.setPriority(Priority.valueOf(taskDTO.getPriority()));
			
			if(!Utility.isNullOrEmpty(taskDTO.getStatus()))
				task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
			
			if(!Utility.isNullOrEmpty(taskDTO.getTitle()))
				task.setTitle((taskDTO.getTitle()));
			
			if(!Utility.isNullOrEmpty(taskDTO.getDueDate()))
				task.setDueDate((taskDTO.getDueDate()));
			task = taskRepository.save(task);
			response.setStatusCode(200);
			response.setStatusMessage("Task Updated");
			response.setResponse(taskMapper.toTaskDTO(task));
		} else {
			throw new TaskException("Invalid Task ID: "+taskId);

		}
		return response;
	}

	@Override
	public PaginatedResponse getTaskByStatus(TaskStatus status, Integer pageNumber, Integer pageSize) {
		if(pageNumber==null)
			pageNumber = 0;
		if(pageSize==null)
			pageSize = 5;
		
		Page<Task> tasks = taskRepository.findByStatus(status,(PageRequest.of(pageNumber, pageSize)));
		List<TaskDTO> tasksList = taskMapper.toTaskDTOs(tasks.getContent());
		PaginatedResponse response = PaginatedResponse.builder().
				body(tasksList).
				pageCount(tasks.getTotalPages()).
				pageNumber(tasks.getNumber())
				.isLast(tasks.isLast()).
				count(tasks.getNumberOfElements())
				.pageSize(tasks.getSize()).build();
		return response;

	}
	
}
