package com.studioreservation.domain.reservation.service;

import com.google.api.client.util.DateTime;
import com.studioreservation.domain.calendar.dto.CalendarRequestDTO;
import com.studioreservation.domain.calendar.service.CalendarService;
import com.studioreservation.domain.dailyrevenue.dto.DailyRevenueDTO;
import com.studioreservation.domain.featuretoggle.service.FeatureToggleService;
import com.studioreservation.domain.platform.dto.PlatformRevenueDTO;
import com.studioreservation.domain.platform.entity.Platform;
import com.studioreservation.domain.platform.repository.PlatformRepository;
import com.studioreservation.domain.reservation.dto.*;
import com.studioreservation.domain.reservation.event.ReservationConfirmedEvent;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.event.ReservationCanceledEvent;
import com.studioreservation.domain.reservation.event.ReservationUpdatedEvent;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.reservation.util.Base32CodeGenerator;
import com.studioreservation.domain.reservation.util.MaskingUtil;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.domain.roominfo.repository.RoomInfoRepository;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final FeatureToggleService featureToggleService;
    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final RoomInfoRepository roomInfoRepository;
    private final ReservationMapper mapper;
    private final CalendarService calendarService;
    private final PlatformRepository platformRepository;
    private final ApplicationEventPublisher eventPublisher;
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

    @Transactional
    public ReservationResponseDTO reserve(Long roomInfoCd, ReservationRequestDTO reservationRequestDTO) {
        RoomInfo roomInfo = roomInfoRepository.findSingleEntity(roomInfoCd);
        ReservationHistory reservationHistory = mapper.toEntity(reservationRequestDTO);
        reservationHistory.setState(ReservationState.WAITING);
        Platform platform = platformRepository.findSingleEntity(1L);

        return createReservation(roomInfo, reservationHistory, platform);
    }

    @Transactional
    public ReservationResponseDTO adminReserve(ReservationChangeRequestDTO requestDTO) {
        ReservationHistory reservationHistory = mapper.toEntity(requestDTO);
        RoomInfo roomInfo = roomInfoRepository.findSingleEntity(requestDTO.getRoomInfoCd());
        Platform platform = platformRepository.findSingleEntity(requestDTO.getPlatformCd());
        reservationHistory.setByAdmin(true);

        if(reservationHistory.getState() == ReservationState.CONFIRMED) {
            eventPublisher.publishEvent(ReservationConfirmedEvent.class);
        }

        return createReservation(roomInfo, reservationHistory, platform);
    }

    public void deleteReservation(String phone, String resvCd) {
        ReservationHistory reservationHistory = repository.findReservationHistory(phone, resvCd);
        repository.delete(reservationHistory);
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

    private ReservationResponseDTO createReservation(RoomInfo roomInfo,
                                                     ReservationHistory reservationHistory,
                                                     Platform platform) {
        reservationHistory.setRoomInfo(roomInfo);
        reservationHistory.setPlatform(platform);
        reservationHistory.calculateTotalPrice();

        String resvCd = generateUniqueReservationCode(LocalDateTime.now());
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
        Platform platform = platformRepository.findSingleEntity(requestDTO.getPlatformCd());
        reservationHistory.updateReservation(requestDTO, platform);

        ReservationResponseDTO reservationResponseDTO = mapper.toDTO(reservationHistory);
        String title = reservationHistory.getRoomInfo().getRoom().getTitle()
                + "_" + MaskingUtil.maskingUserNm(reservationHistory.getUserNm());

        CalendarRequestDTO calendarRequestDTO = CalendarRequestDTO.builder()
                .title(title)
                .startDateTime(new DateTime(reservationHistory.getStrtDt()))
                .endDateTime(new DateTime(reservationHistory.getStrtDt()))
                .dto(reservationResponseDTO)
                .build();

        eventPublisher.publishEvent(new ReservationUpdatedEvent(calendarRequestDTO));

        return reservationResponseDTO;
    }

    @Transactional
    public ReservationResponseDTO updateState(ReservationStateRequestDTO requestDTO,
                                              String phone,
                                              String resvCd) throws Exception {
        ReservationHistory reservationHistory = repository.findReservationHistory(phone, resvCd);
        reservationHistory.updateState(requestDTO.getReservationState());

        ReservationResponseDTO dto = mapper.toDTO(reservationHistory);

            // 상태가 CONFIRMED이면 이벤트 발행
        if (reservationHistory.getState() == ReservationState.CONFIRMED) {
            String title = reservationHistory.getRoomInfo().getRoom().getTitle()
                    + "_" + MaskingUtil.maskingUserNm(reservationHistory.getUserNm());
            CalendarRequestDTO calendarRequestDTO = CalendarRequestDTO.builder()
                   .title(title)
                   .startDateTime(new DateTime(reservationHistory.getStrtDt()))
                   .endDateTime(new DateTime(reservationHistory.getStrtDt()))
                   .dto(dto)
                   .build();

            eventPublisher.publishEvent(new ReservationConfirmedEvent(calendarRequestDTO));
        } else if (reservationHistory.getState() == ReservationState.CANCELED) {
            eventPublisher.publishEvent(new ReservationCanceledEvent(reservationHistory.getSn()));

        }

        return dto;
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

    public List<PlatformRevenueDTO> getStatGroupByPlatform(ReservedTimeReqDTO reservedTimeReqDTO) {
        return repository.findTotalRevenueByPlatformBetween(reservedTimeReqDTO.getStrtDt(), reservedTimeReqDTO.getEndDt());
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
}
