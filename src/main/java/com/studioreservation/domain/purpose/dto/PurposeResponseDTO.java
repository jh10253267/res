package com.studioreservation.domain.purpose.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PurposeResponseDTO {
    private Long cd;
    private String title;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
