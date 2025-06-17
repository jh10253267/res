package com.studioreservation.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.global.formatter.TimestampDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ReservedTimeReqDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Schema(type = "string", example = "예약 시작 시각", description = "예약 시작 시각")
    private Timestamp strtDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Schema(type = "string", example = "예약 종료 시각", description = "예약 종료 시각")
    private Timestamp endDt;
}
