package com.studioreservation.domain.apiuser.dto;

import lombok.Data;

@Data

public class RefreshTokenDTO {
    private String accessToken;
    private String refreshToken;
}
