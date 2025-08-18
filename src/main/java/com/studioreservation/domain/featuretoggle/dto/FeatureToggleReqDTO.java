package com.studioreservation.domain.featuretoggle.dto;

import lombok.Data;

@Data
public class FeatureToggleReqDTO {
    private String featureName;
    private boolean enabled;
}
