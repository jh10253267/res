package com.studioreservation.domain.reservation.dto;

import com.studioreservation.domain.reservation.enums.ReservationState;

public record StateCountDTO(ReservationState state, long count) {
}
