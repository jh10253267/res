package com.studioreservation.domain.reservation.mapper;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

	ReservationHistory toEntity(ReservationRequestDTO reservationRequestDTO);

	@Mapping(source="roomInfo.cd", target="roomInfoCd")
	@Mapping(source="createdAt", target="regDt")
	ReservationResponseDTO toDTO(ReservationHistory reservationHistory);
    ReservationHistory toEntity(ReservationChangeRequestDTO reservationChangeRequestDTO);
}
