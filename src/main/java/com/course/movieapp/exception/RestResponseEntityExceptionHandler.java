package com.course.movieapp.exception;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;
import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	@Value("${debug.response}")
	private boolean debug;

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
		return errorBuilder(ex, request, ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<ErrorMessage> errorBuilder(Exception ex, WebRequest request, String errorMessage,
			HttpStatus status) {
		ErrorMessage message = new ErrorMessage(new Date(), status.value(),
				errorMessage + (debug ? ex.getMessage() : ""), request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, status);
	}
}
