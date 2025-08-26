package com.studioreservation.domain.reservation.service

import com.studioreservation.domain.calendar.service.CalendarService
import com.studioreservation.domain.featuretoggle.service.FeatureToggleService
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO
import com.studioreservation.domain.reservation.entity.ReservationHistory
import com.studioreservation.domain.reservation.enums.PayTyp
import com.studioreservation.domain.reservation.enums.ReservationState
import com.studioreservation.domain.reservation.mapper.ReservationMapper
import com.studioreservation.domain.reservation.repository.ReservationRepository
import com.studioreservation.domain.room.repository.RoomRepository
import com.studioreservation.global.request.PageRequestDTO
import org.springframework.data.domain.PageImpl
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Timestamp
import java.time.LocalDateTime

class ReservationServiceTest extends Specification {
    @Subject
    private ReservationService sut

    private RoomRepository roomRepository = Mock()
    private ReservationRepository repository = Mock()
    private FeatureToggleService featureToggleService = Mock()
    private ReservationMapper reservationMapper = Mock()
    private CalendarService calendarService = Mock()
    private PageRequestDTO pageRequestDTO
    private ReservationHistory reservationHistory
    private ReservationResponseDTO responseDTO

    def setup() {
        sut = new ReservationService(featureToggleService,
                repository,
                roomRepository,
                reservationMapper,
                calendarService)
        pageRequestDTO = PageRequestDTO.builder()
        .page(1)
        .size(10)
        .strtDt(Timestamp.valueOf(LocalDateTime.now()))
        .endDt(Timestamp.valueOf(LocalDateTime.now()))
        .roomCd(1L)
        .phone("01050578403")
        .build()

        responseDTO = ReservationResponseDTO.builder()
        .phone("01050578403")
        .roomCd(1L)
        .payTyp(PayTyp.CASH)
        .userCnt(2)
        .state(ReservationState.COMPLETED)
        .strtDt(Timestamp.valueOf(LocalDateTime.now()))
        .endDt(Timestamp.valueOf(LocalDateTime.now()))
        .regDt(Timestamp.valueOf(LocalDateTime.now()))
        .useParking(true)
        .commission(1)
        .needTaxInvoce(true)
        .senderNm("권준혁")
        .requestCont("전신 거울 부탁해요")
        .policyConfirmed(true)
        .resvCd("tstris")
        .totalAmount(44000)
        .income(1)
        .proposal("test")
        .build()

    }

    def "모든 예약 정보 받아오기"() {
        given:
        def page = new PageImpl<>(List.of(responseDTO), pageRequestDTO.getPageable(), 1);
        repository.findPagedEntities(pageRequestDTO) >> page

        when:
        def result = sut.getAllReservation(pageRequestDTO)

        then:
        result.getDtoList().size() == 1
    }
}
