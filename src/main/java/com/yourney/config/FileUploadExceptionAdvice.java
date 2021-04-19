package com.yourney.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.yourney.model.dto.Message;

@ControllerAdvice
public class FileUploadExceptionAdvice {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Message> handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request,
		      HttpServletResponse response) {
	 
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("No se permiten imágenes superior a 5 MB"));
	}
}
