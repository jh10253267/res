package com.studioreservation.domain.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ReservedTimeResDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Schema(type = "string", example = "예약 시작 시각", description = "예약 시작 시각")
    private Timestamp strtDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Schema(type = "string", example = "예약 종료 시각", description = "예약 종료 시각")
    private Timestamp endDt;
}
