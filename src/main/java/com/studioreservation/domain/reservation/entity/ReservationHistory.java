package com.studioreservation.domain.reservation.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.platform.entity.Platform;
import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.studiofile.entity.StudioFile;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer totalRevenue;

    @Setter
    @Column(unique = true)
    private String resvCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Room room;

    @ManyToOne
    private Platform platform;

    private int commission;

    private int income;

    @Column(length = 50)
    private String email;

    @Builder.Default
    private boolean paymentCompleted = false;

    @ManyToOne
    private CalendarMetaData metaData;

    @OneToMany(mappedBy = "reservationHistory", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<StudioFile> imageSet = new HashSet<>();


// --------------------- 생성 및 업데이트-------------------

    public void updateReservation(ReservationChangeRequestDTO dto) {
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
    }

    public void updateState(ReservationState state) {
        this.state = state;
    }

    public static ReservationHistory buildReservationHistory(ReservationRequestDTO requestDTO, Room room, Platform platform) {
        ReservationHistory reservation = ReservationHistory.builder()
                .userNm(requestDTO.getUserNm())          // DTO.username → userNm
                .phone(requestDTO.getPhone())
                .email(requestDTO.getEmail())
                .requestCont(requestDTO.getRequestCont())  // description → requestCont
                .strtDt(requestDTO.getStrtDt())
                .userCnt(requestDTO.getUserCnt())
                .policyConfirmed(requestDTO.isPolicyConfirmed())
                .room(room)
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
    private static final double DEFAULT_DISCOUNT_RATE = 0.2;
    private static final int EXTRA_PAY_PER_PERSON = 5000;
    private static final int EVENING_HOUR = 18;


}
