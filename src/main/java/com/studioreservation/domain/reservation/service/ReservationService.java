package com.studioreservation.domain.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final ReservationMapper reservationMapper;

	public List<ReservationResponseDTO> getAllReservation() {
		return reservationRepository.findAll()
			.stream()
			.map(reservationMapper::toDTO)
			.toList();
	}

	public PageResponseDTO<ReservationResponseDTO> getPagedReservations(PageRequestDTO pageRequestDTO) {
		Page<ReservationResponseDTO> result = reservationRepository
			.findAll(pageRequestDTO.getPageable(pageRequestDTO.getSortBy()))
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
