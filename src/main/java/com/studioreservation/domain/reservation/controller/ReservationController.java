package com.studioreservation.domain.reservation.controller;

import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService service;
	private final DefaultMessageService messageService;
//	private final SmsProperties smsProperties;

	@PostMapping("/{roomCd}")
	@Operation(summary = "예약하기", description = "예약하기")
	public APIResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
								  @RequestBody ReservationRequestDTO reservationRequestDTO) {
		ReservationResponseDTO responseDTO = service.reserve(roomCd, reservationRequestDTO);
//		Message message = new Message();
//		message.setFrom(smsProperties.getSender());
//		message.setTo(responseDTO.getPhone());
//		message.setText(responseDTO.getUserNm() + "님, \n sms 테스트 메시지입니다. \n 예약 코드: " + responseDTO.getResvCd());
//
//		messageService.sendOne(new SingleMessageSendingRequest(message));

		return APIResponse.success(responseDTO);
	}

	@GetMapping("/my")
	@Operation(summary = "내 예약 조회", description = "내 예약 조회")
	public APIResponse<?> getMyReservation(@RequestParam String phone,
									   @RequestParam String resvCd) {
		return APIResponse.success(service.getReservation(phone, resvCd));
	}

	@GetMapping
	@Operation(summary = "시간 단위 예약 조회", description = "시간 단위 예약 조회")
	public APIResponse<?> getAllReservations(PageRequestDTO requestDTO) {
		return APIResponse.success(service.getAllReservation(requestDTO));
	}
}