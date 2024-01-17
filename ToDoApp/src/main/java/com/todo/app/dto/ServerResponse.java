package com.todo.app.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse<T> {
	
	
	private int statusCode;
	
	private String statusMessage;
	
	private T response;
}
