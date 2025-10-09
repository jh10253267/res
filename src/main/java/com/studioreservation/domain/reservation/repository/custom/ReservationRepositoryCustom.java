package com.studioreservation.domain.reservation.repository.custom;

import com.studioreservation.domain.reservation.dto.*;
import org.springframework.data.domain.Page;

import com.studioreservation.global.request.PageRequestDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRepositoryCustom {
	Page<ReservationAdminResponseDTO> findPagedEntities(PageRequestDTO requestDTO);

	List<ReservedTimeResDTO> findReservedTime(Timestamp strtDt, Timestamp endDt, Long roomCd);

	Integer sumTotalAmount(Timestamp strtDt, Timestamp endDt);

	ReservationStateResponse findCountByState(Timestamp strtDt, Timestamp endDt);
}
