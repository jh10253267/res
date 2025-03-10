package com.studioreservation.domain.room.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.global.response.ApiResponse;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
	private final RoomService roomService;

	@GetMapping
	public ApiResponse<?> getAllRoom() {
		return ApiResponse.success(roomService.getAllRoom());
	}

	@GetMapping("/{roomCd}")
	public ApiResponse<?> getRoom(@PathVariable("roomCd") Long roomCd,
		@RequestParam(required = true) int ymd) {
		System.out.println(ymd);
		return ApiResponse.success(roomService.getRoom(roomCd));
	}

	@PostMapping
	public void makeRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
		roomService.createRoom(roomRequestDTO);
	}

	@PatchMapping("/{roomCd}")
	public ApiResponse<?> editRoom(@PathVariable("roomCd") Long roomCd,
		@RequestBody RoomRequestDTO roomRequestDTO) {
		roomService.editRoom(roomCd, roomRequestDTO);
		return null;
	}
}
