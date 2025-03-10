package com.studioreservation.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private String resultCode;
	private T result;

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<T>("SUCCESS", null);
	}

	public static <T> ApiResponse<T> success(T result) {
		return new ApiResponse<T>("SUCCESS", result);
	}

	public static ApiResponse<Void> error(String resultCode) {
		return new ApiResponse<Void>(resultCode, null);
	}
}
