package com.todo.app.dto;


import java.sql.Timestamp;

import com.todo.app.enums.Priority;
import com.todo.app.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	
	private int taskId;
	
	private String title;
	
	private String description;
	
	private String priority;
	
	private Timestamp dueDate;
	
	private String status;

}