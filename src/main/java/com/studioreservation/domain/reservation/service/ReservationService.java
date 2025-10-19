package com.studioreservation.domain.reservation.service;

import com.google.api.client.util.DateTime;
import com.studioreservation.domain.calendar.service.CalendarService;
import com.studioreservation.domain.dailyrevenue.dto.DailyRevenueDTO;
import com.studioreservation.domain.featuretoggle.service.FeatureToggleService;
import com.studioreservation.domain.reservation.dto.*;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.reservation.util.Base32CodeGenerator;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.enums.RoomType;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final FeatureToggleService featureToggleService;
    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final ReservationMapper mapper;
    private final CalendarService calendarService;
    private static final int MAX_RETRY = 5;

    public PageResponseDTO<ReservationAdminResponseDTO> getAllReservation(PageRequestDTO requestDTO) {
        Page<ReservationAdminResponseDTO> result = repository.findPagedEntities(requestDTO);

        return PageResponseDTO.<ReservationAdminResponseDTO>withAll()
                .dtoList(result.getContent())
                .pageRequestDTO(requestDTO)
                .total(result.getTotalElements())
                .build();
    }

    public List<ReservedTimeResDTO> getReservedTimes(ReservedTimeReqDTO reservedTimeReqDTO) {
        return repository.findReservedTime(reservedTimeReqDTO.getStrtDt(),
                reservedTimeReqDTO.getEndDt(),
                reservedTimeReqDTO.getRoomCd());
    }

    public ReservationResponseDTO getReservation(String phone, String resvCd) {
        return mapper.toDTO(repository.findReservationHistory(phone, resvCd));
    }

    public ReservationResponseDTO reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
        Room room = roomRepository.findSingleEntity(roomCd);

        return createReservationResponseDTO(room, reservationRequestDTO);
    }
    public void adminReserve(ReservationChangeRequestDTO requestDTO) {
        ReservationHistory reservationHistory = mapper.toEntity(requestDTO);
        reservationHistory.setRoom(roomRepository.findSingleEntity(requestDTO.getRoomCd()));
        repository.save(reservationHistory);
    }

    private void checkFeatureEnabled() {
        if (!featureToggleService.isFeatureEnabled("selfPhoto")) {
            throw new StudioException(ErrorCode.FEATURE_DISABLED);
        }
    }

    @Transactional
    public void paymentCompleted(String phone, String resvCd) {
        ReservationHistory reservationHistory = repository.findReservationHistory(phone, resvCd);
        reservationHistory.setPaymentCompleted(true);
    }

    private ReservationResponseDTO createReservationResponseDTO(Room room, ReservationRequestDTO reservationRequestDTO) {
        ReservationHistory reservationHistory = mapper.toEntity(reservationRequestDTO);
        reservationHistory.setRoom(room);

        String resvCd = generateUniqueReservationCode(LocalDateTime.now());
//        reservationHistory.calculateTotalAmount(room);
        reservationHistory.setResvCd(resvCd);

        repository.save(reservationHistory);

        return mapper.toDTO(reservationHistory);
    }

    @Transactional
    public ReservationResponseDTO updateReservation(ReservationChangeRequestDTO requestDTO,
                                                    String phone,
                                                    String resvCd) throws Exception {
        ReservationHistory reservationHistory = repository
                .findReservationHistory(phone, resvCd);
        reservationHistory.updateReservation(requestDTO);

        return mapper.toDTO(reservationHistory);
    }

    @Transactional
    public ReservationResponseDTO updateState(ReservationStateRequestDTO requestDTO,
                                              String phone,
                                              String resvCd) throws Exception {
        ReservationHistory reservationHistory = repository
                .findReservationHistory(phone, resvCd);
        reservationHistory.updateState(requestDTO.getReservationState());

        if (reservationHistory.getState().equals(ReservationState.CONFIRMED)) {
            String title = reservationHistory.getRoom().getTitle() + "_" +
                    maskingUserNm(reservationHistory.getUserNm());

            calendarService.addEvent(title,
                    new DateTime(reservationHistory.getStrtDt()),
                    new DateTime(reservationHistory.getEndDt()));
        }

        return mapper.toDTO(reservationHistory);
    }

    public List<DailyRevenueDTO> getTotalRevenue(Timestamp start, Timestamp end) {
        List<Object[]> results = repository.findDailyRevenueNative(start, end);

        return results.stream()
                .map(row -> new DailyRevenueDTO(
                        (Date) row[0],
                        (BigDecimal) row[1]
                ))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotal(Timestamp start, Timestamp end) {
        return repository.getTotalRevenue(start, end);
    }

    public ReservationStateResponse getCountOfReservations(ReservedTimeReqDTO reservedTimeReqDTO) {
        return repository.findCountByState(reservedTimeReqDTO.getStrtDt(),
                reservedTimeReqDTO.getEndDt());
    }

    private String generateUniqueReservationCode(LocalDateTime localDateTime) {
        int attempts = 0;
        String code;

        do {
            code = Base32CodeGenerator.generateCodeWithDate(localDateTime);
            attempts++;
            if (attempts > MAX_RETRY) {
                throw new RuntimeException("예약 코드 중복으로 인해 생성 실패: 재시도 초과");
            }
        } while (repository.existsByResvCd(code));

        return code;
    }

    private String maskingUserNm(String userNm) {
        if(userNm == null || userNm.isEmpty()) {
            return userNm;
        }
        int length = userNm.length();

        if (length == 1) {
            return userNm;
        } else if (length == 2) {
            return userNm.charAt(0) + "*";
        } else {
            int maskLength = length - 2;
            return userNm.charAt(0)
                    + "*".repeat(maskLength)
                    + userNm.charAt(length - 1);
        }
    }
}
