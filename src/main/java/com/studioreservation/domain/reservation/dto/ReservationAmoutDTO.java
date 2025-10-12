package com.studioreservation.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.global.formatter.TimestampDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReservationAmoutDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Schema(type = "string", example = "언제부터", description = "언제부터")
    private Timestamp strtDt;
}
