package com.ftn.teretana.bean;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path trainingUploadDir = Paths.get("./treninzi-slike");
		String trainingUploadPath = trainingUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/treninzi-slike/**").addResourceLocations("file:/" + trainingUploadPath + "/");
		
	}
}
