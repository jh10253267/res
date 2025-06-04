package com.studioreservation.domain.shootingrequest.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.studioreservation.domain.shootingrequest.dto.ShootingReqResponseDTO;
import com.studioreservation.domain.shootingrequest.dto.ShootingRequestDTO;
import com.studioreservation.domain.shootingrequest.entity.ShootingRequest;

@Mapper(componentModel = "spring")
public interface ShootingRequestMapper {
	ShootingRequestMapper INSTANCE = Mappers.getMapper(ShootingRequestMapper.class);

	ShootingRequest toEntity(ShootingRequestDTO shootingRequestDTO);

	@Mapping(source="purpose.cd", target="purposeCd")
	@Mapping(source="purpose.title", target="purpose")
	ShootingReqResponseDTO toDTO(ShootingRequest shootingRequest);
}