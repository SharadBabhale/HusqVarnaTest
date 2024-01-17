package com.todo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
	
	private int pageNumber;
	
	private int pageSize;
	
	private int pageCount;
	
	private int count;
	
	private boolean isLast;
	
	private T body;
}
