package com.studioreservation.domain.reservation.event;

import com.studioreservation.domain.calendar.dto.CalendarRequestDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationConfirmedEvent {
    private final CalendarRequestDTO calendarRequestDTO;

}
