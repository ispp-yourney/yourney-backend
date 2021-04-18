package com.yourney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {

	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver
	      = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(5242880);
	    return multipartResolver;
	}
	
}
