package com.studioreservation.domain.room.controller;

import org.springframework.security.core.Authentication;
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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
	private final RoomService roomService;

	@GetMapping
	public APIResponse<?> getAllRoom() {
		return APIResponse.success(roomService.getAllRoom());
	}

	@GetMapping("/{roomCd}")
	public APIResponse<?> getRoom(@PathVariable("roomCd") Long roomCd,
		@RequestParam(required = true) int ymd) {
		System.out.println(ymd);
		return APIResponse.success(roomService.getRoom(roomCd));
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PostMapping
	public void makeRoom(@RequestBody RoomRequestDTO roomRequestDTO, Authentication authentication) {
		// 이런식으로 유저의 이름과 권한을 뽑아내기
		System.out.println(authentication.getPrincipal().toString());
		roomService.createRoom(roomRequestDTO);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PatchMapping("/{roomCd}")
	public APIResponse<?> editRoom(@PathVariable("roomCd") Long roomCd,
		@RequestBody RoomRequestDTO roomRequestDTO) {
		roomService.editRoom(roomCd, roomRequestDTO);
		return null;
	}
}
