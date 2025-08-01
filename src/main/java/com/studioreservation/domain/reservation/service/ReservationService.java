package com.studioreservation.domain.reservation.service;

import com.google.api.client.util.DateTime;
import com.studioreservation.domain.calendar.service.CalendarService;
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

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final FeatureToggleService featureToggleService;
    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final ReservationMapper mapper;
    private final CalendarService calendarService;
    private static final int MAX_RETRY = 5;

    public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO requestDTO) {
        Page<ReservationResponseDTO> result = repository.findPagedEntities(requestDTO);

        return PageResponseDTO.<ReservationResponseDTO>withAll()
                .dtoList(result.getContent())
                .pageRequestDTO(requestDTO)
                .total(result.getTotalElements())
                .build();
    }

    public List<ReservedTimeResDTO> getReservedTimes(ReservedTimeReqDTO reservedTimeReqDTO) {
        return repository.findReservedTime(reservedTimeReqDTO.getStrtDt(), reservedTimeReqDTO.getEndDt());
    }

    public ReservationResponseDTO getReservation(String phone, String resvCd) {
        return mapper.toDTO(repository.findReservationHistory(phone, resvCd));
    }

    public ReservationResponseDTO reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
        Room room = roomRepository.findById(roomCd).orElseThrow();
        if (room.getRoomType() == RoomType.SELF) {
            checkFeatureEnabled();
        }

        return getReservationResponseDTO(roomCd, reservationRequestDTO);
    }

    private void checkFeatureEnabled() {
        if (!featureToggleService.isFeatureEnabled("selfPhoto")) {
            throw new StudioException(ErrorCode.FEATURE_DISABLED);
        }
    }

    private ReservationResponseDTO getReservationResponseDTO(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
        ReservationHistory reservationHistory = mapper.toEntity(reservationRequestDTO);
        Room room = roomRepository.findSingleEntity(roomCd);
        reservationHistory.setRoom(room);
        repository.save(reservationHistory);

        String resvCd = generateUniqueReservationCode(reservationHistory.getCreatedAt());
        reservationHistory.calculateTotalAmount(room);
        reservationHistory.setResvCd(resvCd);

        return mapper.toDTO(reservationHistory);
    }

    @Transactional
    public ReservationResponseDTO updateReservation(ReservationChangeRequestDTO requestDTO,
                                                    String phone,
                                                    String resvCd) throws Exception {
        ReservationHistory reservationHistory = repository
                .findReservationHistory(phone, resvCd);
        reservationHistory.updateReservation(requestDTO);

        if (reservationHistory.getState().equals(ReservationState.CONFIRMED)) {
            String title = reservationHistory.getRoom().getTitle() + "_" +
                    maskingUserNm(reservationHistory.getUserNm());

            calendarService.addEvent(title,
                    new DateTime(reservationHistory.getStrtDt()),
                    new DateTime(reservationHistory.getEndDt()));
        }

        return mapper.toDTO(reservationHistory);
    }

    public Integer getTotalAmount(ReservationAmoutDTO amountDTO) {
        return repository.sumTotalAmount(amountDTO.getStrtDt(),
                amountDTO.getEndDt());
    }

    public ReservationStateResponse getCountOfReservations(ReservedTimeReqDTO reservedTimeReqDTO) {
        return repository.findCountByState(reservedTimeReqDTO.getStrtDt(),
                reservedTimeReqDTO.getEndDt());
    }

    private String generateUniqueReservationCode(Timestamp date) {
        int attempts = 0;
        String code;

        do {
            code = Base32CodeGenerator.generateCodeWithDate(date);
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
