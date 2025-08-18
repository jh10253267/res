package com.studioreservation.domain.reservation.enums;

import java.util.Arrays;

public enum PayTyp {
	CARD("CARD"), CASH("CASH");

	private final String typStr;

	PayTyp(String typStr) {
		this.typStr = typStr;
	}

	public static PayTyp fromStr(String typeStr) {
		PayTyp payTyp = Arrays.stream(values())
				.filter(v -> v.typStr.equals(typeStr))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("잘못된 입력값입니다."));

		return payTyp;
	}
}
