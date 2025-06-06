package com.studioreservation.domain.room.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.global.response.APIResponse;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
	private final RoomService service;

	@GetMapping
	@Operation(summary = "예약 가능한 방 조회", description = "예약 가능한 모든 방 조회")
	public APIResponse<?> getAllRoom() {
		return APIResponse.success(service.getAllRoom());
	}

	@GetMapping("/{roomCd}")
	@Operation(summary = "방 정보 상세조회", description = "야직 ymd 데이터를 처리하지 않고있음 아무값이나 넣어도 됨")
	public APIResponse<?> getRoom(@PathVariable("roomCd") Long roomCd,
		@RequestParam(required = true) int ymd) {
		return APIResponse.success(service.getRoom(roomCd));
	}
}
