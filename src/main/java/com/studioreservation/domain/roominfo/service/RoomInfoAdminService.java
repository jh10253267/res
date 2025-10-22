package com.studioreservation.domain.roominfo.service;

import com.studioreservation.domain.room.dto.RoomResponseDTO;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.domain.roominfo.dto.RoomInfoRequestDTO;
import com.studioreservation.domain.roominfo.dto.RoomInfoResponseDTO;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.domain.roominfo.mapper.RoomInfoMapper;
import com.studioreservation.domain.roominfo.repository.RoomInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomInfoAdminService {
    private final RoomInfoRepository repository;
    private final RoomRepository roomRepository;
    private final RoomInfoMapper mapper;

    public RoomInfoResponseDTO createRoomInfo(RoomInfoRequestDTO requestDTO) {
        RoomInfo roomInfo = mapper.toEntity(requestDTO);
        Room room = roomRepository.findSingleEntity(requestDTO.getRoomCd());
        roomInfo.setRoom(room);

        return mapper.toDTO(repository.save(roomInfo));
    }

    @Transactional
    public RoomInfoResponseDTO updateRoomInfo(Long cd,
                                          RoomInfoRequestDTO requestDTO) {
        RoomInfo roomInfo = repository.findSingleEntity(cd);
        roomInfo.updateRoomInfo(requestDTO);

        return mapper.toDTO(roomInfo);
    }
}
