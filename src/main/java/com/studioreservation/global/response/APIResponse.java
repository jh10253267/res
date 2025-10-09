package com.studioreservation.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class APIResponse<T> {
	private String resultCode;
	private T result;

	public static <T> APIResponse<T> success() {
		return new APIResponse<T>("SUCCESS", null);
	}

	public static <T> APIResponse<T> success(T result) {
		return new APIResponse<T>("SUCCESS", result);
	}

	public static APIResponse<Void> error(String resultCode) {
		return new APIResponse<Void>(resultCode, null);
	}

    public static <T> APIResponse<T> error(T result) {
        return new APIResponse<T>("", result);
    }
}
