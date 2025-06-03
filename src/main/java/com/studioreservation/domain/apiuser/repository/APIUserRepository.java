package com.studioreservation.domain.apiuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.apiuser.entity.APIUser;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

}
