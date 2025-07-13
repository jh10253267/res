package com.studioreservation.domain.featuretoggle.entity;

import com.studioreservation.domain.featuretoggle.dto.FeatureToggleReqDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureToggle {
    @Id
    private String featureName;

    @Column(nullable = false)
    private boolean enabled;

    public void updateFeature(FeatureToggleReqDTO reqDTO) {
        if(reqDTO.getFeatureName() != null) this.featureName = reqDTO.getFeatureName();
        if(reqDTO.isEnabled() != this.enabled) this.enabled = reqDTO.isEnabled();
    }
}
