package com.studioreservation.domain.reservation.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final ReservationMapper reservationMapper;

	public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO pageRequestDTO) {
		Specification<ReservationHistory> spec = Specification.where(null);

		if (pageRequestDTO.getStrtDt() != null || pageRequestDTO.getEndDt() != null) {
			spec = spec.and((root, query, cb) -> {
				Path<Timestamp> strtDt = root.get("strtDt");
				Path<Timestamp> endDt = root.get("endDt");

				Predicate p = cb.conjunction();

				if (pageRequestDTO.getEndDt() != null) {
					p = cb.and(p, cb.greaterThanOrEqualTo(endDt, pageRequestDTO.getStrtDt()));
				}
				if (pageRequestDTO.getStrtDt() != null) {
					p = cb.and(p, cb.lessThanOrEqualTo(strtDt, pageRequestDTO.getEndDt()));
				}
				return p;
			});
		}

		Page<ReservationResponseDTO> result = reservationRepository
			.findAll(spec, pageRequestDTO.getPageable(pageRequestDTO.getSortBy()))
			.map(entity -> reservationMapper.toDTO(entity));

		return PageResponseDTO.<ReservationResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(pageRequestDTO)
			.total(result.getTotalElements())
			.build();
	}

	public PageResponseDTO<ReservationResponseDTO> getReservationsByRoomCd(Long roomCd, PageRequestDTO pageRequestDTO) {
		Specification<ReservationHistory> spec = Specification.where(null);

		spec = spec.and((root, query, cb) -> {
			Join<ReservationHistory, Room> roomJoin = root.join("room", JoinType.LEFT);
			return cb.equal(roomJoin.get("cd"), roomCd);
		});

		if (pageRequestDTO.getStrtDt() != null || pageRequestDTO.getEndDt() != null) {
			spec = spec.and((root, query, cb) -> {
				Path<Timestamp> strtDt = root.get("strtDt");
				Path<Timestamp> endDt = root.get("endDt");

				Predicate p = cb.conjunction();

				if (pageRequestDTO.getEndDt() != null) {
					p = cb.and(p, cb.greaterThanOrEqualTo(endDt, pageRequestDTO.getStrtDt()));
				}
				if (pageRequestDTO.getStrtDt() != null) {
					p = cb.and(p, cb.lessThanOrEqualTo(strtDt, pageRequestDTO.getEndDt()));
				}
				return p;
			});
		}

		Page<ReservationResponseDTO> result = reservationRepository
			.findAll(spec, pageRequestDTO.getPageable(pageRequestDTO.getSortBy()))
			.map(entity -> reservationMapper.toDTO(entity));

		return PageResponseDTO.<ReservationResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(pageRequestDTO)
			.total(result.getTotalElements())
			.build();
	}

	public ReservationResponseDTO reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
		ReservationHistory reservationHistory = reservationMapper.toEntity(reservationRequestDTO);
		Room room = roomRepository.findSingleEntity(roomCd);
		reservationHistory.setRoom(room);

		return reservationMapper.toDTO(reservationRepository.save(reservationHistory));
	}
}
