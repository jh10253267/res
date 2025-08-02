package com.studioreservation.domain.reservation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor

public enum ReservationState {
    WAITING("00", "예약대기", "WAITING"),
    CONFIRMED("01", "예약확정", "CONFIRMED"),
    CANCELED("02", "예약취소", "CANCELED"),
    COMPLETED("03", "사용완료", "COMPLETED");

    private final String code;
    private final String description;
    private final String stateStr;

    public static ReservationState fromCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public static ReservationState fromStr(String stateStr) {
        return Arrays.stream(values())
                .filter(status -> status.stateStr.equals(stateStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력값입니다."));
    }
}

