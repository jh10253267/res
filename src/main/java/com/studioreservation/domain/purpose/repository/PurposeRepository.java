package com.studioreservation.domain.purpose.repository;

import com.studioreservation.domain.purpose.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurposeRepository extends JpaRepository<Purpose, Long> {

}
