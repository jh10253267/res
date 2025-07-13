package com.studioreservation.domain.featuretoggle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeatureToggleResDTO {
    private String featureName;
    private boolean enabled;
}
