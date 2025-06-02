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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping
	@Operation(summary = "예약 조회", description = "페이징 관련 파라미터를 넣지 않고 쿼리 스트링을 넣으면 단건 조회")
	public APIResponse<?> getAllReservation(PageRequestDTO pageRequestDTO,
		@Parameter(description = "핸드폰 번호")
		@RequestParam(required = false) String phone,
		@Parameter(description = "예약 코드")
		@RequestParam(required = false) String resvCd) {
		if (phone != null && resvCd != null) {
			return APIResponse.success(reservationService.getReservation(phone, resvCd));
		} else {
			return APIResponse.success(reservationService.getAllReservation(pageRequestDTO));
		}
	}

	@GetMapping("/{roomCd}")
	@Operation(summary = "특정 방 예약 조회", description = "특정 방 예약 조회")
	public APIResponse<?> getReservationsByRoomCd(PageRequestDTO pageRequestDTO,
		@PathVariable(value = "roomCd") Long roomCd) {
		return APIResponse.success(reservationService.getReservationsByRoomCd(pageRequestDTO, roomCd));
	}

	@PostMapping("/{roomCd}")
	@Operation(summary = "예약", description = "")
	public APIResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
		@RequestBody ReservationRequestDTO reservationRequestDTO) {
		return APIResponse.success(reservationService.reserve(roomCd, reservationRequestDTO));
	}
	@PutMapping("/{roomCd}")
	@Operation(summary = "예약 상태 변경", description = "현재 요청 구조 피드백 필요")
	public APIResponse<?> changeState(@RequestBody StateChangeRequestDTO stateChangeRequestDTO) {
		return APIResponse.success(reservationService.changeState(stateChangeRequestDTO));
	}
}