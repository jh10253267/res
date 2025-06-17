package com.studioreservation.domain.platform.repository;

import com.studioreservation.domain.platform.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

}
