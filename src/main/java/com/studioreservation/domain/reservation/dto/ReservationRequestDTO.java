package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.studiofile.dto.StudioFileDTO;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
	@Schema(description = "예약자 명", example = "예약자 명")
	private String userNm;

	@Schema(description = "연락처", example = "연락처")
	private String phone;

	@Schema(description = "결제 수단", example = "결제 수단")
	private PayTyp payTyp;

	@Schema(description = "예약 인원수", example = "예약 인원수")
	private int userCnt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "예약 시작 시각", description = "예약 시작 시각")
	private Timestamp strtDt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "예약 종료 시각", description = "예약 종료 시각")
	private Timestamp endDt;

	@Schema(description = "이메일", example = "이메일")
	@NotBlank(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$",
			message = "이메일 형식이 올바르지 않습니다.")
	@Size(min = 7, max = 50)
	private String email;

	@Schema(description = "주차권 필요 여부", example = "주차권 필요 여부")
	private boolean useParking;

	@Schema(description = "세금 계산서 필요 여부", example = "세금 계산서 필요 여부")
	private boolean needTaxInvoce;

	@Schema(description = "입금자", example = "입금자")
	private String senderNm;

	@Schema(description = "대여 목적", example = "대여 목적")
	private String proposal;

	@Schema(description = "요청사항", example = "요청사항")
	private String requestCont;

	@Schema(description = "약관 동의 여부", example = "약관 동의 여부")
	private boolean policyConfirmed;

    @Schema(example = "파일 이름")
    private List<StudioFileDTO> fileNames;
}
