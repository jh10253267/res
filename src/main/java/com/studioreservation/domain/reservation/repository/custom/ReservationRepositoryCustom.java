package com.studioreservation.domain.reservation.repository.custom;

import org.springframework.data.domain.Page;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.global.request.PageRequestDTO;

public interface ReservationRepositoryCustom {
	Page<ReservationResponseDTO> findPagedEntities(PageRequestDTO requestDTO, Long roomCd);
}
