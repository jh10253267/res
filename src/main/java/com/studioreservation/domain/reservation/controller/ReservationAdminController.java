package com.studioreservation.domain.reservation.controller;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/reservations")
public class ReservationAdminController {
    private final ReservationService service;
    @GetMapping
    @Operation(summary = "예약 조회", description = "페이징 관련 파라미터를 넣지 않고 쿼리 스트링을 넣으면 단건 조회")
    public APIResponse<?> getAllReservation(PageRequestDTO pageRequestDTO,
                                            @Parameter(description = "핸드폰 번호")
                                            @RequestParam(required = false) String phone,
                                            @Parameter(description = "예약 코드")
                                            @RequestParam(required = false) String resvCd) {
        if (phone != null && resvCd != null) {
            return APIResponse.success(service.getReservation(phone, resvCd));
        } else {
            return APIResponse.success(service.getAllReservation(pageRequestDTO));
        }
    }

    @GetMapping("/{roomCd}")
    @Operation(summary = "방별 예약 조회", description = "페이징 관련 파라미터를 넣지 않고 쿼리 스트링을 넣으면 단건 조회")
    public APIResponse<?> getAllReservationsBySn(@PathVariable("roomCd") Long roomCd,
                                                 PageRequestDTO requestDTO) {
        return APIResponse.success(service.getReservationsByRoomCd(requestDTO, roomCd));
    }

    @PutMapping
    @Operation(summary = "예약 상태 변경", description = "현재 요청 구조 피드백 필요")
    public APIResponse<?> changeState(@RequestBody ReservationChangeRequestDTO reservationChangeRequestDTO,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("resvCd") String resvCd) {
        return APIResponse.success(service.updateReservation(reservationChangeRequestDTO, phone, resvCd));
    }
}
