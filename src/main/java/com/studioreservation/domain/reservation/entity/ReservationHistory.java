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

    @Builder.Default
    private boolean byAdmin = false;

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
    private static final BigDecimal DEFAULT_DISCOUNT_RATE = BigDecimal.valueOf(0.8); // 20% 할인
    private static final BigDecimal EXTRA_PAY_PER_PERSON_PER_HALF_HOUR = BigDecimal.valueOf(2500); // 인원 1명당 30분 추가요금
    private static final BigDecimal NIGHT_PRICE_PER_HALF_HOUR = BigDecimal.valueOf(10000);   // 00:00~08:59
    private static final BigDecimal EVENING_PRICE_PER_HALF_HOUR = BigDecimal.valueOf(15000); // 18:00~23:59
    private static final long HALF_HOUR_MILLIS = 30 * 60 * 1000;

//    계산식에 9시간 이상 예약 시 전체 금액 10% 할인 적용

    /**
     * 전체 총액 계산 (A룸이면 할인, 그 외는 기본)
     */
    public void calculateTotalPrice() {
        if (roomInfo.getRoom().isDiscountApplicable()) {
            calculateDiscountedPrice();
        } else {
            calculateRegularPrice();
        }
        int duration = calculateHalfHourUnits();
        if(duration < 9) {
            this.totalRevenue = this.totalRevenue.multiply(BigDecimal.valueOf(1.1));
        }
    }

    /**
     * 30분 단위 할인 적용 계산 (A타입 전용)
     */
    private void calculateDiscountedPrice() {
        LocalDateTime start = strtDt.toLocalDateTime();
        LocalDateTime end = endDt.toLocalDateTime();

        BigDecimal total = BigDecimal.ZERO;
        LocalDateTime current = start;

        while (current.isBefore(end)) {
            LocalTime currentTime = current.toLocalTime();
            BigDecimal price = getDiscountedPricePerHalfHour(currentTime);
            total = total.add(price);
            current = current.plusMinutes(30);
        }

        // 인원 초과 시 추가요금 포함
        total = total.add(applyExtraPay());

        this.totalRevenue = total;
    }

    /**
     * 일반 룸 요금 계산 (시간대 상관없이 동일 요금)
     */
    private void calculateRegularPrice() {
        int halfHourUnits = calculateHalfHourUnits();
        BigDecimal basePrice = roomInfo.getHalfHrPrice().multiply(BigDecimal.valueOf(halfHourUnits));
        BigDecimal total = basePrice.add(applyExtraPay());
        this.totalRevenue = total;
    }

    /**
     * 할인 시간대별 30분 요금 반환
     */
    private BigDecimal getDiscountedPricePerHalfHour(LocalTime time) {
        if (!time.isBefore(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(9, 0))) {
            return NIGHT_PRICE_PER_HALF_HOUR;
        }
        if (!time.isBefore(LocalTime.of(18, 0))) {
            return EVENING_PRICE_PER_HALF_HOUR;
        }
        return roomInfo.getHalfHrPrice();
    }

    /**
     * 30분 단위로 총 이용 시간 계산
     */
    private int calculateHalfHourUnits() {
        long durationMillis = endDt.getTime() - strtDt.getTime();
        return (int) Math.ceil((double) durationMillis / HALF_HOUR_MILLIS);
    }

    /**
     * 초과 인원에 따른 추가요금 계산 (30분 단위)
     */
    private BigDecimal applyExtraPay() {
        int capacity = roomInfo.getRoom().getCapacity();
        if (userCnt <= capacity) {
            return BigDecimal.ZERO;
        }

        int extraUserCnt = userCnt - capacity;
        int halfHourUnits = calculateHalfHourUnits();

        return EXTRA_PAY_PER_PERSON_PER_HALF_HOUR
                .multiply(BigDecimal.valueOf(extraUserCnt))
                .multiply(BigDecimal.valueOf(halfHourUnits));
    }
}
