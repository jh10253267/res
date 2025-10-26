package com.studioreservation.domain.reservation.event;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationCanceledEvent {
    private final Long sn;
}
