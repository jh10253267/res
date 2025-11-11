package com.studioreservation.domain.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatformRevenueDTO {
    private String platform;
    private Object totalRevenue;
    private Object totalCount;
}

