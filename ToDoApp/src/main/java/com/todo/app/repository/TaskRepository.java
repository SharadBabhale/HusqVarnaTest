package com.todo.app.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.app.entity.Task;
import com.todo.app.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	public Page<Task> findAll(Pageable pageable);
	
	public Page<Task> findByStatus(TaskStatus status, Pageable pageable);
	
}
