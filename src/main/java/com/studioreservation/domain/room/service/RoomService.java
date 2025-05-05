package com.studioreservation.domain.room.service;

import java.util.List;

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
	private final RoomRepository roomRepository;
	private final RoomMapper roomMapper;

	public List<RoomResponseDTO> getAllRoom() {
		return roomRepository.findAllByUseYnTrue()
			.stream().map(entity -> roomMapper.toRoomDTO(entity))
			.toList();
	}

	public RoomResponseDTO getRoom(Long roomCd) {
		return roomMapper.toRoomDTO(roomRepository.findSingleEntity(roomCd));
	}

	public RoomResponseDTO createRoom (RoomRequestDTO roomRequestDTO){
		roomRepository.save(roomMapper.toRoom(roomRequestDTO));
		return null;
	}

	@Transactional
	public void editRoomInfo(Long roomCd, RoomRequestDTO roomRequestDTO) {
		Room room = roomRepository.findById(roomCd).orElseThrow();
		room.updateEntity(roomRequestDTO);
	}
}
