package com.todo.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.dto.PaginatedResponse;
import com.todo.app.dto.ServerResponse;
import com.todo.app.dto.TaskDTO;
import com.todo.app.entity.User;
import com.todo.app.enums.TaskStatus;
import com.todo.app.exception.TaskException;
import com.todo.app.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping(value = "/addTask")
	public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {
		taskDTO = taskService.saveTask(taskDTO);
		return taskDTO;
	}

	@GetMapping(value = "/getTasks")
	public PaginatedResponse getTasks(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		PaginatedResponse taskDTO = taskService.getTasks(pageNumber, pageSize);
		return taskDTO;
	}

	@GetMapping(value = "/getTasksByStatus/{status}")
	public PaginatedResponse getTasksByStatus(@PathVariable("status") TaskStatus status,
			@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {

			PaginatedResponse taskDTO = taskService.getTaskByStatus(status, pageNumber, pageSize);
			return taskDTO;		
	}

	@GetMapping(value = "/getTasks/{taskId}")
	public ServerResponse<TaskDTO> addTask(@PathVariable("taskId") int taskId) {
		ServerResponse<TaskDTO> taskDTO = taskService.getTask(taskId);
		return taskDTO;
	}

	@DeleteMapping(value = "/deleteTask/{taskId}")
	public ServerResponse<String> deleteTask(@PathVariable("taskId") int taskId) {
		ServerResponse<String> taskDTO = taskService.deleteTask(taskId);
		return taskDTO;
	}

	@PatchMapping(value = "/updateTask/{taskId}")
	public ServerResponse updateTask(@RequestBody TaskDTO taskDTO, @PathVariable("taskId") int taskId) {
		ServerResponse response = taskService.updateTask(taskDTO, taskId);
		return response;
	}

}
