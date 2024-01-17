package com.todo.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.todo.app.dto.ServerResponse;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(TaskException.class)
	public ResponseEntity<ServerResponse<String>> handleTaskException(TaskException ex){
		ServerResponse<String> response = new ServerResponse<String>();
		response.setResponse(ex.getMessage());
		response.setStatusCode(400);
		return ResponseEntity.status(400).body(response);
	}
	
}
