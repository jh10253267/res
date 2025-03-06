package com.studioreservation.domain.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studioreservation.domain.reservation.dto.ReservationDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final ReservationMapper reservationMapper;

	public List<ReservationDTO> getAllReservation() {
		return reservationRepository.findAll().stream()
			.map(entity -> reservationMapper.toDTO(entity))
			.toList();
	}

	public void reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
		ReservationHistory reservationHistory = reservationMapper.toEntity(reservationRequestDTO);
		Room room = roomRepository.findSingleEntity(roomCd);
		reservationHistory.setRoom(room);
		reservationRepository.save(reservationHistory);
	}
}
