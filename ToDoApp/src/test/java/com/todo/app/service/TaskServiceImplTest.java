package com.todo.app.service;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.todo.app.dto.PaginatedResponse;
import com.todo.app.dto.ServerResponse;
import com.todo.app.dto.TaskDTO;
import com.todo.app.entity.Task;
import com.todo.app.enums.Priority;
import com.todo.app.enums.TaskStatus;
import com.todo.app.exception.TaskException;
import com.todo.app.mapper.TaskMapper;
import com.todo.app.repository.TaskRepository;

class TaskServiceImplTest {
	
	@Mock
	TaskMapper taskMapper;
	
	@Mock
	TaskRepository taskRepository;
	
	@InjectMocks
	TaskServiceImpl taskService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testSaveTaskValidationException() {
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		assertThrows(TaskException.class, ()->taskService.saveTask(taskDTO));
	}
	
	@Test
	void testSaveTask() {
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Task task = Mockito.mock(Task.class);
		Mockito.when(taskMapper.toTask(taskDTO)).thenReturn(task);

		Mockito.when(task.getTitle()).thenReturn("Java programming");
		Mockito.when(taskDTO.getTitle()).thenReturn("Java programming");
		Mockito.when(taskRepository.save(task)).thenReturn(task);
		Mockito.when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);
		TaskDTO taskDtoRes = taskService.saveTask(taskDTO);
		assertEquals(taskDtoRes.getTitle(), "Java programming");
	
	}

	

	@Test
	void testGetTasks() {
		Page<Task> taskPage = Mockito.mock(Page.class);
		List<Task> tasks = new ArrayList<Task>();
		Task task = Mockito.mock(Task.class);
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Mockito.when(task.getTitle()).thenReturn("Java Programming");
		tasks.add(task);
		Mockito.when(taskDTO.getTitle()).thenReturn("Java Programming");
		taskDTOs.add(taskDTO);
		Mockito.when(taskRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(taskPage);
		Mockito.when(taskPage.getContent()).thenReturn(tasks);
		Mockito.when(taskMapper.toTaskDTOs(Mockito.anyList())).thenReturn(taskDTOs);
		Mockito.when(taskPage.getSize()).thenReturn(5);
		Mockito.when(taskPage.getNumberOfElements()).thenReturn(2);
		Mockito.when(taskPage.getContent()).thenReturn(tasks);
		PaginatedResponse<List<TaskDTO>> taskPaginatedResponse = taskService.getTasks(null,null);
		assertEquals(5,taskPaginatedResponse.getPageSize());
		assertEquals(2,taskPaginatedResponse.getCount());
		assertEquals("Java Programming",taskPaginatedResponse.getBody().get(0).getTitle() );
		

	}

	@Test
	void testGetTask() {
		Task task = Mockito.mock(Task.class);
		Mockito.when(task.getTitle()).thenReturn("Java Programming");
		Optional<Task> taskOptional = Optional.of(task);
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Mockito.when(taskDTO.getTitle()).thenReturn("Java Programming");
		Mockito.when(taskMapper.toTaskDTO(Mockito.any())).thenReturn(taskDTO);		
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		ServerResponse<TaskDTO> response = taskService.getTask(12);
		assertEquals(200, response.getStatusCode());
		assertEquals("Java Programming", response.getResponse().getTitle());
	}

	@Test
	void testGetTaskException() {
		Optional<Task> taskOptional = Optional.empty();
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		assertThrows(TaskException.class, ()-> taskService.getTask(12));
	}
	
	@Test
	void testDeleteTask() {
		Task task = Mockito.mock(Task.class);
		Optional<Task> taskOptional = Optional.of(task);
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		ServerResponse<String> response = taskService.deleteTask(12);
		assertEquals("Task Deleted: "+12, response.getStatusMessage());
		assertEquals(200, response.getStatusCode());
	
	}
	@Test
	void testDeleteTaskException() {
		Optional<Task> taskOptional = Optional.empty();
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		assertThrows(TaskException.class, ()-> taskService.deleteTask(12));
	}

	@Test
	void testUpdateTask() {
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Task task = Mockito.mock(Task.class);
		Optional<Task> taskOptional = Optional.of(task);
		Mockito.when(task.getTitle()).thenReturn("java Programming");
		Mockito.when(taskMapper.toTaskDTO(Mockito.any())).thenReturn(taskDTO);
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		ServerResponse<TaskDTO> response = taskService.updateTask(taskDTO, 12);
		Mockito.when(response.getResponse().getTitle()).thenReturn("java Programming");
	}
	
	@Test
	void testUpateTaskException() {
		Optional<Task> taskOptional = Optional.empty();
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Mockito.when(taskRepository.findById(Mockito.anyInt())).thenReturn(taskOptional);
		assertThrows(TaskException.class, ()-> taskService.updateTask(taskDTO, 12));
	}
	
	@Test
	void testUpdateTaskValidationException() {
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Mockito.when(taskDTO.getPriority()).thenReturn("Abc");
		assertThrows(TaskException.class, ()->taskService.updateTask(taskDTO, 12));
	}

	
	@Test
	void testGetTaskByStatus() {
		Page<Task> taskPage = Mockito.mock(Page.class);
		List<Task> tasks = new ArrayList<Task>();
		Task task = Mockito.mock(Task.class);
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
		Mockito.when(task.getTitle()).thenReturn("Java Programming");
		tasks.add(task);
		Mockito.when(taskDTO.getTitle()).thenReturn("Java Programming");
		taskDTOs.add(taskDTO);
		Mockito.when(taskRepository.findByStatus(Mockito.any(),Mockito.any(PageRequest.class))).thenReturn(taskPage);
		Mockito.when(taskPage.getContent()).thenReturn(tasks);
		Mockito.when(taskMapper.toTaskDTOs(Mockito.anyList())).thenReturn(taskDTOs);
		Mockito.when(taskPage.getSize()).thenReturn(5);
		Mockito.when(taskPage.getNumberOfElements()).thenReturn(2);
		Mockito.when(taskPage.getContent()).thenReturn(tasks);
		PaginatedResponse<List<TaskDTO>> taskPaginatedResponse = taskService.getTaskByStatus(TaskStatus.COMPLETED, null,null);
		assertEquals(5,taskPaginatedResponse.getPageSize());
		assertEquals(2,taskPaginatedResponse.getCount());
		assertEquals("Java Programming",taskPaginatedResponse.getBody().get(0).getTitle() );
		

	}

	
}
