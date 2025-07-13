package com.studioreservation.domain.featuretoggle.service;

import com.studioreservation.domain.featuretoggle.dto.FeatureToggleReqDTO;
import com.studioreservation.domain.featuretoggle.dto.FeatureToggleResDTO;
import com.studioreservation.domain.featuretoggle.entity.FeatureToggle;
import com.studioreservation.domain.featuretoggle.mapper.FeatureToggleMapper;
import com.studioreservation.domain.featuretoggle.repository.FeatureToggleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeatureToggleService {
    private final FeatureToggleRepository repository;
    private final FeatureToggleMapper mapper;

    public void registerFeature(FeatureToggleReqDTO reqDTO) {
        FeatureToggle featureToggle = mapper.toEntity(reqDTO);
        repository.save(featureToggle);
    }

    public void updateFeature(FeatureToggleReqDTO reqDTO, String featureName) {
        FeatureToggle featureToggle = repository.findByFeatureName(featureName)
                .orElseThrow();
        featureToggle.updateFeature(reqDTO);
    }

    public boolean isFeatureEnabled(String featureName) {
        return repository.findByFeatureName(featureName)
                .orElseThrow()
                .isEnabled();
    }

    public List<FeatureToggleResDTO> getAllFeature() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO).collect(Collectors.toList());
    }
}
