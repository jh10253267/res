package com.studioreservation.domain.roominfo.service;

import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.domain.roominfo.dto.RoomInfoRequestDTO;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.domain.roominfo.mapper.RoomInfoMapper;
import com.studioreservation.domain.roominfo.repository.RoomInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomInfoAdminService {
    private final RoomInfoRepository repository;
    private final RoomRepository roomRepository;
    private final RoomInfoMapper mapper;

    public void createRoomInfo(RoomInfoRequestDTO requestDTO) {
        RoomInfo roomInfo = mapper.toEntity(requestDTO);
        Room room = roomRepository.findSingleEntity(requestDTO.getRoomCd());
        roomInfo.setRoom(room);
        repository.save(roomInfo);
    }
}
