package com.studioreservation.domain.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.StateChangeRequestDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping
	public APIResponse<?> getAllReservation(PageRequestDTO pageRequestDTO,
		@RequestParam(required = false) String phone,
		@RequestParam(required = false) String resvCd) {
		if (phone != null && resvCd != null) {
			return APIResponse.success(reservationService.getReservation(phone, resvCd));
		} else {
			return APIResponse.success(reservationService.getAllReservation(pageRequestDTO));
		}
	}

	@GetMapping("/{roomCd}")
	public APIResponse<?> getReservationsByRoomCd(@PathVariable(value = "roomCd") Long roomCd,
		PageRequestDTO pageRequestDTO) {
		return APIResponse.success(reservationService.getReservationsByRoomCd(roomCd, pageRequestDTO));
	}

	@PostMapping("/{roomCd}")
	public APIResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
		@RequestBody ReservationRequestDTO reservationRequestDTO) {
		return APIResponse.success(reservationService.reserve(roomCd, reservationRequestDTO));
	}
	@PutMapping("/{roomCd}")
	public APIResponse<?> changeState(@RequestBody StateChangeRequestDTO stateChangeRequestDTO) {
		return APIResponse.success(reservationService.changeState(stateChangeRequestDTO));
	}
}