package com.studioreservation.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
	ALREADY_LIKED_POST(HttpStatus.CONFLICT, "user already like the post"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
	NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connect to notification occurs error"),
	;

	private final HttpStatus status;
	private final String message;
}