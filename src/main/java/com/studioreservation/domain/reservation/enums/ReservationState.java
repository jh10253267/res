package com.studioreservation.domain.reservation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor

public enum ReservationState {
    WAITING("00", "예약대기"),
    CONFIRMED("01", "예약확정"),
    CANCELED("02", "예약취소"),
    COMPLETED("03", "사용완료");

    private final String code;
    private final String description;

    public static ReservationState fromCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }
}

