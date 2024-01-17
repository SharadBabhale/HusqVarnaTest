package com.todo.app.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SWebMcvConfigurer implements WebMvcConfigurer {

	
	/**
	 * @author Sharad
	 * It will add Cors Mapping 
	 * @param registry the CorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}



}