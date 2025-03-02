package com.studioreservation.domain.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping
	public ApiResponse<?> getAllReservation() {
		return ApiResponse.ok(reservationService.getAllReservation());
	}

	@PostMapping("/{roomCd}")
	public ApiResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
		@RequestBody ReservationRequestDTO reservationRequestDTO) {
		reservationService.reserve(roomCd, reservationRequestDTO);
		return null;
	}
}
