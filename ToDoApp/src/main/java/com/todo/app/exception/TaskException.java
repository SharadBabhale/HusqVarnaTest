package com.todo.app.exception;

public class TaskException extends RuntimeException{
	
	private String message;
	
	public TaskException(String message) {
		super(message);
		this.message = message;
	}
	
}
