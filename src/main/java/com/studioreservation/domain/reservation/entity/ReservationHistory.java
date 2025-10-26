package com.studioreservation.domain.reservation.entity;

import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.platform.entity.Platform;
import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.domain.studiofile.entity.StudioFile;
import com.studioreservation.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sn;

    private String userNm;

    private String phone;

    @Enumerated(EnumType.STRING)
    private PayTyp payTyp;

    private int userCnt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    ReservationState state = ReservationState.WAITING;

    private Timestamp strtDt;

    private Timestamp endDt;

    private Boolean useParking;

    private Boolean needTaxInvoce;

    private String senderNm;

    private String proposal;

    private String requestCont;

    private boolean policyConfirmed;

    private String memo = "";

    @Setter
    private BigDecimal totalRevenue;

    @Setter
    @Column(unique = true)
    private String resvCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private RoomInfo roomInfo;

    @ManyToOne
    private Platform platform;

    private BigDecimal commission;

    private BigDecimal income;

    @Column(length = 50)
    private String email;

    @Builder.Default
    private boolean paymentCompleted = false;

    @OneToOne(mappedBy = "reservationHistory", cascade = CascadeType.REMOVE)
    private CalendarMetaData calendarMetaData;

    @OneToMany(mappedBy = "reservationHistory", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<StudioFile> imageSet = new HashSet<>();


// --------------------- 생성 및 업데이트-------------------
    public void updateReservation(ReservationChangeRequestDTO dto, Platform platform) {
        if (dto.getUserNm() != null) this.userNm = dto.getUserNm();
        if (dto.getPhone() != null) this.phone = dto.getPhone();
        if (dto.getPayTyp() != null) this.payTyp = dto.getPayTyp();
        if (dto.getUserCnt() != null) this.userCnt = dto.getUserCnt();
        if (dto.getStrtDt() != null) this.strtDt = dto.getStrtDt();
        if (dto.getEndDt() != null) this.endDt = dto.getEndDt();
        if (dto.getUseParking() != null) this.useParking = dto.getUseParking();
        if (dto.getNeedTaxInvoce() != null) this.needTaxInvoce = dto.getNeedTaxInvoce();
        if (dto.getSenderNm() != null) this.senderNm = dto.getSenderNm();
        if (dto.getProposal() != null) this.proposal = dto.getProposal();
        if (dto.getRequestCont() != null) this.requestCont = dto.getRequestCont();
        if (dto.getPolicyConfirmed() != null) this.policyConfirmed = dto.getPolicyConfirmed();
        if (dto.getMemo() != null) this.memo = dto.getMemo();
        if (dto.getTotalRevenue() != null) this.totalRevenue = dto.getTotalRevenue();
        this.platform = platform;
    }

    public void updateState(ReservationState state) {
        this.state = state;
    }

    public static ReservationHistory buildReservationHistory(ReservationRequestDTO requestDTO, RoomInfo roomInfo, Platform platform) {
        ReservationHistory reservation = ReservationHistory.builder()
                .userNm(requestDTO.getUserNm())          // DTO.username → userNm
                .phone(requestDTO.getPhone())
                .email(requestDTO.getEmail())
                .requestCont(requestDTO.getRequestCont())  // description → requestCont
                .strtDt(requestDTO.getStrtDt())
                .userCnt(requestDTO.getUserCnt())
                .policyConfirmed(requestDTO.isPolicyConfirmed())
                .roomInfo(roomInfo)
                .platform(platform)
                .build();

        if (requestDTO.getFileNames() != null) {
            requestDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.getSavedFileName().split("_");
                reservation.addImage(arr[0], arr[1], fileName.getSavedFileUrl());
            });
        }

        return reservation;
    }

    public void addImage(String uuid, String fileName, String savedFileUrl) {
        StudioFile studioFile = StudioFile.builder()
                .uuid(uuid)
                .fileName(fileName)
                .savedFileUrl(savedFileUrl)
                .shootingRequest(null)
                .reservationHistory(this)
                .build();

        this.imageSet.add(studioFile);
    }

    public void clearImages() {
        imageSet.forEach(studioFile -> {
            studioFile.changeRequest(null);
            this.imageSet.clear();
        });
    }

    //-------------------------비즈니스 규칙 ----------------------
    private static final BigDecimal DEFAULT_DISCOUNT_RATE = BigDecimal.ONE.subtract(BigDecimal.valueOf(0.2));
    private static final BigDecimal EXTRA_PAY_PER_PERSON = BigDecimal.valueOf(5500);
    private static final BigDecimal NIGHT_DISCOUNT_PRICE_PER_HALF_HOUR = BigDecimal.valueOf(10000);   // 자정~9시 까지는 2만원
    private static final BigDecimal EVENING_DISCOUNT_PRICE_PER_HALF_HOUR = BigDecimal.valueOf(15000); // 오후 6부터 자정까지는 3만원
    private static final int EVENING_HOUR = 18;
    private static final int PARTY_ROOM_CD = 1;
    private static final long UNIT_MILLiS = 30 * 60 * 1000;

    private int calculateDurationHalfUnit() {
        long durationMillis = endDt.getTime() - strtDt.getTime();
        long durationHours = (long) Math.ceil((double) durationMillis / UNIT_MILLiS);

        return (int) (durationHours);
    }

    public BigDecimal calculateDiscountedPrice() {
        LocalDateTime start = this.strtDt.toLocalDateTime();
        LocalDateTime end = this.endDt.toLocalDateTime();

        BigDecimal total = BigDecimal.ZERO;

        LocalDateTime current = start;
        while (current.isBefore(end)) {
            LocalDateTime nextHour = current.plusMinutes(30);
            BigDecimal price = getDiscountRate(current.toLocalTime());
            total = total.add(price);
            current = nextHour;
        }

        return total;
    }

    private BigDecimal getDiscountRate(LocalTime time) {
        if (time.isAfter(LocalTime.MIDNIGHT.minusSeconds(1)) && time.isBefore(LocalTime.of(9, 0))) {
            return NIGHT_DISCOUNT_PRICE_PER_HALF_HOUR; // 50% 요금
        }
        if (time.isAfter(LocalTime.of(18, 0)) && time.isBefore(LocalTime.MIDNIGHT)) {
            return EVENING_DISCOUNT_PRICE_PER_HALF_HOUR; // 30% 할인
        }
        return this.roomInfo.getHalfHrPrice();
    }

    private BigDecimal applyExtraPay() {
        BigDecimal extraPay = BigDecimal.ZERO;
        // 만약 방의 수용 가능 인원보다 예약 희망 인원이 많다면
        if(roomInfo.getRoom().getCapacity() < userCnt) {
            int extraUserCnt = userCnt - roomInfo.getRoom().getCapacity();
            extraPay = extraPay.multiply(BigDecimal.valueOf(extraUserCnt));
        }

        return extraPay;
    }

    public void calculateTotalRevenue() {
        int halfHourDurationUnit = calculateDurationHalfUnit(); // 4
        BigDecimal extraPay = applyExtraPay();
        BigDecimal defaultRevenue = BigDecimal.valueOf(halfHourDurationUnit).multiply(roomInfo.getHalfHrPrice()); // 88,000
        this.totalRevenue = defaultRevenue.add(extraPay);
    }
}
