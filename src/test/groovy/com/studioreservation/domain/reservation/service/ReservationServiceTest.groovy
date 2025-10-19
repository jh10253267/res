package com.studioreservation.domain.reservation.service

import com.studioreservation.domain.calendar.service.CalendarService
import com.studioreservation.domain.featuretoggle.service.FeatureToggleService
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO
import com.studioreservation.domain.reservation.entity.ReservationHistory
import com.studioreservation.domain.reservation.enums.PayTyp
import com.studioreservation.domain.reservation.enums.ReservationState
import com.studioreservation.domain.reservation.mapper.ReservationMapper
import com.studioreservation.domain.reservation.repository.ReservationRepository
import com.studioreservation.domain.room.entity.Room
import com.studioreservation.domain.room.enums.RoomType
import com.studioreservation.domain.room.repository.RoomRepository
import com.studioreservation.global.request.PageRequestDTO
import org.springframework.data.domain.PageImpl
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Timestamp
import java.time.LocalDateTime

class ReservationServiceTest extends Specification {
//    @Subject
//    private ReservationService sut
//
//    private RoomRepository roomRepository = Mock()
//    private ReservationRepository repository = Mock()
//    private FeatureToggleService featureToggleService = Mock()
//    private ReservationMapper reservationMapper = Mock()
//    private CalendarService calendarService = Mock()
//    private PageRequestDTO pageRequestDTO
//    private ReservationRequestDTO requestDTO
//    private ReservationHistory reservationHistory
//    private ReservationResponseDTO responseDTO
//    private Room room
//
//    def setup() {
//        sut = new ReservationService(featureToggleService,
//                repository,
//                roomRepository,
//                reservationMapper,
//                calendarService)
//        pageRequestDTO = PageRequestDTO.builder()
//        .page(1)
//        .size(10)
//        .strtDt(Timestamp.valueOf(LocalDateTime.now()))
//        .endDt(Timestamp.valueOf(LocalDateTime.now()))
//        .roomCd(1L)
//        .phone("01050578403")
//        .build()
//
//        responseDTO = ReservationResponseDTO.builder()
//        .phone("01050578403")
//        .roomCd(1L)
//        .payTyp(PayTyp.CASH)
//        .userCnt(2)
//        .state(ReservationState.COMPLETED)
//        .strtDt(Timestamp.valueOf(LocalDateTime.now()))
//        .endDt(Timestamp.valueOf(LocalDateTime.now()))
//        .regDt(Timestamp.valueOf(LocalDateTime.now()))
//        .useParking(true)
//        .commission(1)
//        .needTaxInvoce(true)
//        .senderNm("권준혁")
//        .requestCont("전신 거울 부탁해요")
//        .policyConfirmed(true)
//        .resvCd("tstris")
//        .totalAmount(44000)
//        .income(1)
//        .proposal("test")
//        .build()
//
//        requestDTO = ReservationRequestDTO.builder()
//        .senderNm("test")
//        .needTaxInvoce(true)
//        .requestCont("test")
//        .policyConfirmed(true)
//        .proposal("test")
//        .phone("01050578403")
//        .strtDt(Timestamp.valueOf(LocalDateTime.now()))
//        .endDt(Timestamp.valueOf(LocalDateTime.now()))
//        .build()
//
//        room = Room.builder()
//                .cd(1L)
//                .capacity(2)
//                .name("A")
//                .description("A 룸")
//                .dayPrice(44000)
//                .halfHrPrice(22000)
//                .roomType(RoomType.NORMAL)
//                .useYn(true)
//                .build()
//
//        reservationHistory = ReservationHistory.builder()
//                .senderNm("test")
//                .needTaxInvoce(true)
//                .requestCont("test")
//                .policyConfirmed(true)
//                .proposal("test")
//                .phone("01050578403")
//                .strtDt(Timestamp.valueOf(LocalDateTime.now()))
//                .endDt(Timestamp.valueOf(LocalDateTime.now()))
//                .build()
//
//    }
//
//    def "모든 예약 정보 받아오기"() {
//        given:
//        def page = new PageImpl<>(List.of(responseDTO), pageRequestDTO.getPageable(), 1);
//        repository.findPagedEntities(pageRequestDTO) >> page
//
//        when:
//        def result = sut.getAllReservation(pageRequestDTO)
//
//        then:
//        result.getDtoList().size() == 1
//    }
//
//    def "예약하기"() {
//        given:
//        Long roomCd = 1L
//        roomRepository.findSingleEntity(roomCd) >> room
//        reservationMapper.toEntity(requestDTO) >> reservationHistory
//        reservationMapper.toDTO(reservationHistory) >> responseDTO
//
//        when:
//        def result = sut.reserve(roomCd, requestDTO)
//
//        then:
//        result.getPhone() == "01050578403"
//    }
}
