package com.studioreservation.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.user.entity.APIUser;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

}
