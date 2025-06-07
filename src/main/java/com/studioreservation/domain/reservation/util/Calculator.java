package com.studioreservation.domain.reservation.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Calculator {
    public int calculate(Timestamp strtDt, Timestamp endDt) {
        long durationMillis = endDt.getTime() - strtDt.getTime(); // 밀리초 차이
        long durationHours = (long) Math.ceil(durationMillis / (1000.0 * 60 * 60)); // 올림 처리

        return (int) (durationHours);
    }
}
