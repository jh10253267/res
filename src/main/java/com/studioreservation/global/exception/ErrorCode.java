package com.studioreservation.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
	ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "Username already exists"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
	NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connect to notification occurs error"),
	// 예약 관련
	NO_SUCH_RESERVATION(HttpStatus.NOT_FOUND, "일치하는 예약 내역이 없습니다."),
	// 방 관련
	NO_SUCH_ROOM(HttpStatus.NOT_FOUND, "해당하는 방이 없습니다."),
	// 카테고리 관련
	NO_SUCH_PURPOSE(HttpStatus.NOT_FOUND, "해당하는 촬영목적이 없습니다.")
	;

	private final HttpStatus status;
	private final String message;
}