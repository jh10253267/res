package com.studioreservation.domain.calendar.dto;

import com.google.api.client.util.DateTime;
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CalendarRequestDTO {
    private String title;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private ReservationResponseDTO dto;
}
