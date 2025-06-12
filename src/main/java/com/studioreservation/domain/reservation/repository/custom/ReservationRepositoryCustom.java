package com.studioreservation.domain.reservation.repository.custom;

import com.studioreservation.domain.reservation.dto.ReservedTimeReqDTO;
import com.studioreservation.domain.reservation.dto.ReservedTimeResDTO;
import org.springframework.data.domain.Page;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.global.request.PageRequestDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRepositoryCustom {
	Page<ReservationResponseDTO> findPagedEntities(PageRequestDTO requestDTO);

	List<ReservedTimeResDTO> findReservedTime(Timestamp strtDt, Timestamp endDt);

	Integer sumTotalAmount(Timestamp strtDt, Timestamp endDt);
}
