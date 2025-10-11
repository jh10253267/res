package com.studioreservation.domain.reservation.dto;

import com.studioreservation.domain.reservation.enums.ReservationState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationStateRequestDTO {
    @Schema(description = "예약 상태", example = "WAITING, CONFIRMED, CANCELED, COMPLETED")
    private ReservationState reservationState;
}
