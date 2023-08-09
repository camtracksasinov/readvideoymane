package com.camtrack.captcha;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.camtrack.config.StaticValues;
import com.camtrack.entities.Success;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ForbiddenException.class)
	ResponseEntity<?> handleException(ForbiddenException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body((Object) new Success(ex.getMessage(), StaticValues.InvalidCaptcha_Int));
		// return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(),
		// HttpStatus.FORBIDDEN);
	}
}
