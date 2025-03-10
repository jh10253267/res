package com.studioreservation.domain.reservation.mapper;

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

	@Mapping(source="room.cd", target="roomCd")
	ReservationResponseDTO toDTO(ReservationHistory reservationHistory);
}
