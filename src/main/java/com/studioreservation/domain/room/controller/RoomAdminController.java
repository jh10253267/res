package com.studioreservation.domain.room.controller;

import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.service.RoomService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/rooms")
public class RoomAdminController {
    private final RoomService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "모든 방 조회", description = "모든 방 조회")
    public APIResponse<?> getAllRooms() {
        return APIResponse.success(service.getAllRoomsForAdmin());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "방 생성", description = "방 생성")
    public APIResponse<?> createRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        service.createRoom(roomRequestDTO);
        return APIResponse.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roomCd}")
    @Operation(summary = "방 정보 수정", description = "방 정보 수정")
    public APIResponse<?> editRoomInfo(@PathVariable("roomCd") Long roomCd,
                                       @RequestBody RoomRequestDTO roomRequestDTO) {
        return APIResponse.success(service.updateRoomInfo(roomCd, roomRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roomCd}")
    @Operation(summary = "방 삭제", description = "방 삭제")
    public APIResponse<?> deleteRoomInfo(@PathVariable("roomCd") Long roomCd) {
        service.deleteRoomInfo(roomCd);

        return APIResponse.success();
    }


}
