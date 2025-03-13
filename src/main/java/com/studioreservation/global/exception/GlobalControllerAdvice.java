package com.studioreservation.global.exception;

import static com.studioreservation.global.exception.ErrorCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.studioreservation.global.response.APIResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
	@ExceptionHandler(StudioreservationApplicationException.class)
	public ResponseEntity<?> errorHandler(StudioreservationApplicationException e) {
		log.error("Error occurs {}", e.toString());
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(APIResponse.error(e.getErrorCode().name()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
		log.error("Error occurs {}", e.toString());
		return ResponseEntity.status(DATABASE_ERROR.getStatus())
			.body(APIResponse.error(DATABASE_ERROR.name()));
	}
}