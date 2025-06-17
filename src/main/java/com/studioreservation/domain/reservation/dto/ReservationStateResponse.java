package com.studioreservation.domain.reservation.dto;

import java.util.List;

public record ReservationStateResponse(
        Long count,
        List<StateCountDTO> dtoList
) {
}
