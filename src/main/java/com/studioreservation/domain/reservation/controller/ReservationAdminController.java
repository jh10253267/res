package com.studioreservation.domain.reservation.controller;

import com.studioreservation.domain.dailyrevenue.dto.DailyRevenueDTO;
import com.studioreservation.domain.reservation.dto.*;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/reservations")
@Slf4j
public class ReservationAdminController {
    private final ReservationService service;

    @PostMapping
    public APIResponse<?> createReservation(@RequestBody ReservationChangeRequestDTO requestDTO) {
        log.info("createReservation platformId: {}", requestDTO.getPlatformCd());
        log.info("createReservation roomInfoCd: {}", requestDTO.getPlatformCd());

        return APIResponse.success(service.adminReserve(requestDTO));
    }

    @GetMapping
    @Operation(summary = "예약 조회", description = "예약 조회, 만약 페이징된 자료를 보고싶다면 size를 설정하면 됨")
    public APIResponse<?> getAllReservation(PageRequestDTO pageRequestDTO) {
        return APIResponse.success(service.getAllReservation(pageRequestDTO));
    }

    @PutMapping
    @Operation(summary = "예약 정보 변경", description = "예약 정보 변경")
    public APIResponse<?> changeState(@RequestBody ReservationChangeRequestDTO reservationChangeRequestDTO,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("resvCd") String resvCd) throws Exception {
        return APIResponse.success(service.updateReservation(reservationChangeRequestDTO, phone, resvCd));
    }

    @PutMapping("/states")
    @Operation(summary = "예약 상태 변경", description = "예약 상태 변경")
    public APIResponse<?> updateReservation(@RequestBody ReservationStateRequestDTO reservationStateRequestDTO,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("resvCd") String resvCd) throws Exception {
        return APIResponse.success(service.updateState(reservationStateRequestDTO, phone, resvCd));
    }

    @DeleteMapping()
    @Operation(summary = "예약 삭제", description = "예약 삭제")
    public APIResponse<?> deleteReservation(@RequestParam("phone") String phone,
                                            @RequestParam("resvCd") String resvCd) throws Exception {
        service.deleteReservation(phone, resvCd);
        return APIResponse.success();
    }

    @GetMapping("/revenues")
    @Operation(summary = "수입", description = "수입")
    public APIResponse<?> getAllRevenue(ReservedTimeResDTO reservedTimeResDTO) {
        Map<String, Object> map= new HashMap<>();
        List<DailyRevenueDTO> result = service.getTotalRevenue(reservedTimeResDTO.getStrtDt(), reservedTimeResDTO.getEndDt());
        BigDecimal totalRevenue = service.getTotal(reservedTimeResDTO.getStrtDt(), reservedTimeResDTO.getEndDt());
        totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        map.put("totalRevenue", totalRevenue);
        map.put("dailyTotals", result);

        return APIResponse.success(map);
    }

    @GetMapping("/statistics/count")
    public APIResponse<?>  getCountOfReservation(ReservedTimeReqDTO reservedTimeReqDTO) {
        return APIResponse.success(service.getCountOfReservations(reservedTimeReqDTO));
    }

    @PostMapping("paymentCompleted")
    public APIResponse<?> paymentCompleted(@RequestParam("phone") String phone,
                                           @RequestParam("resvCd") String resvCd) {
        service.paymentCompleted(phone, resvCd);
        return APIResponse.success();
    }
}
