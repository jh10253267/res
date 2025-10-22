package com.studioreservation.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.ReservationState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationAdminResponseDTO {
    @Schema(description = "DB상 예약 식별 번호", example = "DB상 예약 식별 번호")
    private Long sn;

    @Schema(description = "예약한 스튜디오 번호", example = "예약한 스튜디오 번호")
    private Long roomInfoCd;

    @Schema(description = "예약한 이름", example = "예약한 이름")
    private String userNm;

    @Schema(description = "연락처", example = "연락처")
    private String phone;

    @Schema(description = "결제 수단", example = "결재 수단")
    private PayTyp payTyp;

    @Schema(description = "예약 인원", example = "예약 인원")
    private int userCnt;

    @Schema(description = "상태", example = "상태")
    private ReservationState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Schema(type = "string", example = "대여 시작 시각", description = "대여 시작 시각")
    private Timestamp strtDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Schema(type = "string", example = "대여 종료 시각", description = "대여 종료 시각")
    private Timestamp endDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Schema(type = "string", example = "잘 모르겠음...", description = "잘 모르겠음...")
    private Timestamp regDt;

    @Schema(description = "주차권 필요 여부", example = "주차권 필요 여부")
    private boolean useParking;

    @Schema(description = "예약 인원", example = "예약 인원")
    private boolean needTaxInvoce;

    @Schema(description = "잘 모르겠음...", example = "잘 모르겠음...")
    private String senderNm;

    @Schema(description = "대여 목적", example = "대여 목적")
    private String proposal;

    @Schema(description = "잘 모르겠음...", example = "잘 모르겠음...")
    private String requestCont;

    @Schema(description = "약관 동의 여부", example = "약관 동의 여부")
    private boolean policyConfirmed;

    @Schema(description = "예약 코드", example = "예약 코드")
    private String resvCd;

    @Schema(description = "총 금액", example = "총 금액")
    private BigDecimal totalRevenue;

    @Schema(description = "수수료", example = "수수료")
    private BigDecimal commission;

    @Schema(description = "순수입", example = "순수입")
    private BigDecimal income;

    @Schema(description = "메모", example = "메모")
    private String memo;

    @Schema(description = "플랫폼 번호", example = "플랫폼 번호")
    private Long platformCd;
}

