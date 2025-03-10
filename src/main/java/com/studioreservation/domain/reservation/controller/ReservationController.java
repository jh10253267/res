package com.studioreservation.domain.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	// 모든 방에 대한 정보(모든 정보, 페이징된 정보)
	@GetMapping
	public ApiResponse<?> getAllReservation(PageRequestDTO pageRequestDTO) {
		return ApiResponse.success(reservationService.getAllReservation(pageRequestDTO));
	}

	// 특정 방에 대한 정보(모든 정보, 페이징된 정보)
	@GetMapping("/{roomCd}")
	public ApiResponse<?> getReservationsByRoomCd(@PathVariable(value = "roomCd") Long roomCd,
		PageRequestDTO pageRequestDTO) {
		return ApiResponse.success(reservationService.getReservationsByRoomCd(roomCd, pageRequestDTO));
	}

	@PostMapping("/{roomCd}")
	public ApiResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
		@RequestBody ReservationRequestDTO reservationRequestDTO) {
		return ApiResponse.success(reservationService.reserve(roomCd, reservationRequestDTO));
	}
}
