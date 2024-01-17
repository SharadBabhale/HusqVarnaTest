package com.todo.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.todo.app.dto.TaskDTO;
import com.todo.app.entity.Task;
import com.todo.app.enums.Priority;
import com.todo.app.enums.TaskStatus;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	
	@Mapping(expression = "java(getStatusEnumValue(task.getStatus()))", target = "status")
	@Mapping(expression = "java(getPriorityEnumValue(task.getPriority()))", target = "priority")
	public TaskDTO toTaskDTO(Task task);
	
	public Task toTask(TaskDTO task);
	
	public List<TaskDTO> toTaskDTOs(List<Task> task);

	default String getStatusEnumValue(TaskStatus status) {
		return status.name();
	}
	
	default String getPriorityEnumValue(Priority status) {
		
		return status.name();
	}
}
