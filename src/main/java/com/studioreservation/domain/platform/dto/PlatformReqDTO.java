package com.studioreservation.domain.platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlatformReqDTO {
    private Long cd;
    private String platform;
    private String memo;
}
