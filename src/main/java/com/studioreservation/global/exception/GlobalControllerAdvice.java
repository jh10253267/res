package com.studioreservation.global.exception;

import static com.studioreservation.global.exception.ErrorCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.studioreservation.global.response.APIResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
	@ExceptionHandler(StudioException.class)
	public ResponseEntity<?> errorHandler(StudioException e) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationErrorHandler(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.toString());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest()
                .body(APIResponse.error(errors));
    }



}