package com.studioreservation.domain.featuretoggle.repository;

import com.studioreservation.domain.featuretoggle.entity.FeatureToggle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, String> {
    Optional<FeatureToggle> findByFeatureName(String featureName);
}
