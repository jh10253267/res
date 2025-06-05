package com.studioreservation.domain.reservation.controller;

import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.service.ReservationService;
import com.studioreservation.global.config.SmsProperties;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;
	private final DefaultMessageService messageService;
//	private final SmsProperties smsProperties;

	@PostMapping("/{roomCd}")
	@Operation(summary = "예약하기", description = "예약하기")
	public APIResponse<?> reserve(@PathVariable("roomCd") Long roomCd,
								  @RequestBody ReservationRequestDTO reservationRequestDTO) {
		ReservationResponseDTO responseDTO = reservationService.reserve(roomCd, reservationRequestDTO);
//		Message message = new Message();
//		message.setFrom(smsProperties.getSender());
//		message.setTo(responseDTO.getPhone());
//		message.setText(responseDTO.getUserNm() + "님, \n sms 테스트 메시지입니다. \n 예약 코드: " + responseDTO.getResvCd());
//
//		messageService.sendOne(new SingleMessageSendingRequest(message));

		return APIResponse.success(responseDTO);
	}
}