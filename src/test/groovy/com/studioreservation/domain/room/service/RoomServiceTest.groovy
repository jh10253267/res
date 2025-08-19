package com.studioreservation.domain.room.service

import com.studioreservation.domain.room.dto.RoomRequestDTO
import com.studioreservation.domain.room.dto.RoomResponseDTO
import com.studioreservation.domain.room.entity.Room
import com.studioreservation.domain.room.enums.RoomType
import com.studioreservation.domain.room.mapper.RoomMapper
import com.studioreservation.domain.room.repository.RoomRepository
import spock.lang.Specification
import spock.lang.Subject

class RoomServiceTest extends Specification {
    @Subject
    private RoomService roomService

    private RoomRepository roomRepository = Mock()
    private RoomMapper roomMapper = Mock()

    private List<Room> roomList
    private RoomRequestDTO requestDTO
    private RoomResponseDTO responseDTO

    def setup() {
        roomService = new RoomService(roomRepository, roomMapper)
        roomList = [
                Room.builder()
                        .cd(1L)
                        .capacity(2)
                        .name("A")
                        .description("A 룸")
                        .dayPrice(44000)
                        .halfHrPrice(22000)
                        .roomType(RoomType.NORMAL)
                        .useYn(true)
                        .build()
        ]

        requestDTO = RoomRequestDTO.builder()
                .capacity(2)
                .name("A")
                .description("A 룸")
                .dayPrice(44000)
                .halfHrPrice(22000)
                .roomType(RoomType.NORMAL)
                .build()

        responseDTO = RoomResponseDTO.builder()
                .capacity(2)
                .name("A")
                .description("A 룸")
                .dayPrice(44000)
                .halfHrPrice(22000)
                .roomType(RoomType.NORMAL)
                .build()
    }

    def "Get Rooms"() {
        given:
        roomRepository.findAllByUseYnTrueOrderByCdDesc() >> roomList

        when:
        List<RoomResponseDTO> result = roomService.getAllRoom()

        then:
        result.size() == 2
    }

    def "Get Room"() {
        given:
        roomRepository.findSingleEntity(1L) >> roomList[0]
        roomMapper.toDTO(roomList[0]) >> RoomResponseDTO.builder()
                .cd(1L)
                .name("A")
                .description("A 룸")
                .capacity(2)
                .dayPrice(44000)
                .halfHrPrice(22000)
                .roomType(RoomType.NORMAL)
                .build()

        when:
        def result = roomService.getRoom(1L)

        then:
        result.getName() == "A"
    }

    def "Create Room"() {
        given:
        roomMapper.toEntity(requestDTO) >> roomList[0]
        roomRepository.save(roomList[0]) >> roomList[0]
        roomMapper.toDTO(roomList[0]) >> responseDTO

        when:
        def roomResponseDTO = roomService.createRoom(requestDTO)

        then:
        roomResponseDTO.getName() == "A"
    }
}
