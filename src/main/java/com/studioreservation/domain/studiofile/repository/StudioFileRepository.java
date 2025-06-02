package com.studioreservation.domain.studiofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studioreservation.domain.studiofile.entity.StudioFile;

@Repository
public interface StudioFileRepository extends JpaRepository<StudioFile, Long> {
}
