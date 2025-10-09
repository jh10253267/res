package com.studioreservation.domain.reservation.controller;

import com.studioreservation.domain.reservation.dto.ReservationAmoutDTO;
import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservedTimeReqDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/reservations")
public class ReservationAdminController {
    private final ReservationService service;

    @GetMapping
    @Operation(summary = "예약 조회", description = "예약 조회, 만약 페이징된 자료를 보고싶다면 size를 설정하면 됨")
    public APIResponse<?> getAllReservation(PageRequestDTO pageRequestDTO) {
        return APIResponse.success(service.getAllReservation(pageRequestDTO));
    }

    @PutMapping
    @Operation(summary = "예약 상태 변경", description = "예약 상태 변경")
    public APIResponse<?> changeState(@RequestBody ReservationChangeRequestDTO reservationChangeRequestDTO,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("resvCd") String resvCd) throws Exception {
        return APIResponse.success(service.updateReservation(reservationChangeRequestDTO, phone, resvCd));
    }

    @GetMapping("/amounts")
    @Operation(summary = "수입", description = "수입")
    public APIResponse<?> getAllAmount(ReservationAmoutDTO amountDTO) {
        return APIResponse.success(service.getTotalAmount(amountDTO));
    }

    @GetMapping("/statistics/count")
    public APIResponse<?>  getCountOfResevation(ReservedTimeReqDTO reservedTimeReqDTO) {
        return APIResponse.success(service.getCountOfReservations(reservedTimeReqDTO));
    }
}
