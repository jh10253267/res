package com.studioreservation.global.security.exception;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException {
	TOKEN_ERROR token_error;

	public enum TOKEN_ERROR {
		UNACCEPT(HttpStatus.UNAUTHORIZED, "Token is null or too short"),
		BADTYPE(HttpStatus.UNAUTHORIZED, "Token type Bearer"),
		MALFORM(HttpStatus.UNAUTHORIZED, "Malformed Token"),
		BADSIGN(HttpStatus.UNAUTHORIZED, "BadSignatured Token"),
		EXPIRED(HttpStatus.UNAUTHORIZED, "Expired Token");

		private HttpStatus status;
		private String msg;

		TOKEN_ERROR(HttpStatus status, String msg) {
			this.status = status;
			this.msg = msg;
		}

		public HttpStatus getStatus() {
			return this.status;
		}

		public String getMsg() {
			return this.msg;
		}
	}

	public AccessTokenException(TOKEN_ERROR error) {
		super(error.name());
		this.token_error = error;
	}

	public void sendResponseError(HttpServletResponse response) {
		response.setStatus(token_error.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Gson gson = new Gson();

		String responseStr = gson.toJson(Map.of("msg", token_error.getMsg(), "time", new Date()));

		try {
			response.getWriter().println(responseStr);
		} catch (IOException e) {
			throw new RuntimeException((e));
		}
	}
}