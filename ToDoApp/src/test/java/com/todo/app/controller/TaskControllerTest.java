package com.todo.app.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.app.dto.PaginatedResponse;
import com.todo.app.dto.ServerResponse;
import com.todo.app.dto.TaskDTO;
import com.todo.app.enums.TaskStatus;
import com.todo.app.service.TaskService;
import com.todo.app.service.TaskServiceImpl;

public class TaskControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testAddTask() throws Exception {

        TaskDTO taskDTO = new TaskDTO(); 
        taskDTO.setTitle("Java Programming");
        Mockito.when(taskService.saveTask(Mockito.any())).thenReturn(taskDTO);

        String taskJson = objectMapper.writeValueAsString(taskDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/tasks/addTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TaskDTO responseTaskDTO = objectMapper.readValue(responseBody, TaskDTO.class);
        
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Java Programming", responseTaskDTO.getTitle());
        
        verify(taskService, times(1)).saveTask(Mockito.any());
    }

    @Test
    public void testGetTasks() throws Exception {
        PaginatedResponse paginatedResponse = new PaginatedResponse(); 
        paginatedResponse.setCount(5);
        paginatedResponse.setPageNumber(0);
        Mockito.when(taskService.getTasks(Mockito.any(), Mockito.any())).thenReturn(paginatedResponse);

        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tasks/getTasks")
                .param("pageNumber", "1")
                .param("pageSize", "10");

        // Act
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaginatedResponse response = objectMapper.readValue(responseBody, PaginatedResponse.class);
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getCount());

        verify(taskService, times(1)).getTasks(1, 10);
    }

    @Test
    public void testGetTasksByStatus() throws Exception {
        PaginatedResponse paginatedResponse = new PaginatedResponse(); 
        paginatedResponse.setCount(5);
        paginatedResponse.setPageNumber(0);
        Mockito.when(taskService.getTaskByStatus(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(paginatedResponse);

        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tasks/getTasksByStatus/TODO")
                .param("pageNumber", "1")
                .param("pageSize", "10");

        // Act
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaginatedResponse response = objectMapper.readValue(responseBody, PaginatedResponse.class);
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getCount());

        verify(taskService, times(1)).getTaskByStatus(TaskStatus.TODO,1, 10);
    }
    
    @Test
    public void testGetTask() throws Exception {
        // Arrange
        int taskId = 1;

        
        ServerResponse<TaskDTO> serverResponse = new ServerResponse<>(); 
        TaskDTO taskDTO = TaskDTO.builder().title("Python Programming").priority(TaskStatus.TODO.name()).build();
        serverResponse.setStatusCode(200);
        serverResponse.setResponse(taskDTO);
        Mockito.when(taskService.getTask(Mockito.anyInt())).thenReturn(serverResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tasks/getTasks/{taskId}", taskId);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ServerResponse<TaskDTO> response = objectMapper.readValue(responseBody, ServerResponse.class);
        assertEquals(200, result.getResponse().getStatus());


        verify(taskService, times(1)).getTask(taskId);
    }
    
    @Test
    public void testDeleteTask() throws Exception {
        // Arrange
        int taskId = 1; 

        ServerResponse serverResponse = ServerResponse.builder().statusCode(200).statusMessage("Task Deleted").build();
        
        Mockito.when(taskService.deleteTask(Mockito.anyInt())).thenReturn(serverResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/tasks/deleteTask/{taskId}", taskId);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ServerResponse<String> response = objectMapper.readValue(responseBody, ServerResponse.class);
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Task Deleted", response.getStatusMessage());


        verify(taskService, times(1)).deleteTask(taskId);
    }
    
    @Test
    public void testUpdateTask() throws Exception {
        int taskId = 1; 
        TaskDTO taskDTO = TaskDTO.builder().title("Java Programming").build(); 

        
        ServerResponse serverResponse = ServerResponse.builder().
        		statusCode(200).
        		response(taskDTO).
        		build();

        Mockito.when(taskService.updateTask(Mockito.any(), Mockito.anyInt())).thenReturn(serverResponse);

        String taskJson = objectMapper.writeValueAsString(taskDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/tasks/updateTask/{taskId}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ServerResponse response = objectMapper.readValue(responseBody, ServerResponse.class);
        assertEquals(200, response.getStatusCode());

        verify(taskService, times(1)).updateTask(Mockito.any(), Mockito.anyInt());
    }
    
}