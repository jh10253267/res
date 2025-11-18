package com.studioreservation.domain.room.service;

import java.util.List;
import java.util.stream.Collectors;

import com.studioreservation.domain.room.dto.RoomAdminResponseDTO;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.domain.roominfo.mapper.RoomInfoMapper;
import com.studioreservation.domain.roominfo.repository.RoomInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studioreservation.domain.room.dto.RoomResponseDTO;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.mapper.RoomMapper;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository repository;
	private final RoomMapper mapper;
    private final RoomInfoMapper infoMapper;
    private final RoomInfoRepository  roomInfoRepository;

	public List<RoomResponseDTO> getAllRoom() {
        List<Room> rooms = repository.findAllByOrderByOrderIndexAsc();

        rooms.forEach(room -> {
            List<RoomInfo> filteredInfos = room.getRoomInfos().stream()
                    .filter(RoomInfo::isUseYn)
                    .toList();
            room.setRoomInfos(filteredInfos);
        });

        return mapper.toDTOs(rooms);
	}

    public List<RoomAdminResponseDTO> getAllRoomsForAdmin() {
        List<Room> rooms = repository.findAllByOrderByOrderIndexAsc();
        return mapper.toAdminDTOs(rooms);
    }

	public RoomResponseDTO getRoom(Long roomCd) {
		return mapper.toDTO(repository.findSingleEntity(roomCd));
	}

    @Transactional
	public void createRoom (RoomRequestDTO roomRequestDTO){
		repository.save(mapper.toEntity(roomRequestDTO));
	}

	@Transactional
	public RoomResponseDTO updateRoomInfo(Long roomCd, RoomRequestDTO roomRequestDTO) {
		Room room = repository.findSingleEntity(roomCd);
		room.updateEntity(roomRequestDTO);

		return mapper.toDTO(room);
	}

	public void deleteRoomInfo(Long roomCd) {
		repository.deleteById(roomCd);
	}
}
