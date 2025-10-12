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
	private final RoomRepository repository;
	private final RoomMapper mapper;

	public List<RoomResponseDTO> getAllRoom() {
		return repository.findAllByUseYnTrueOrderByCdAsc()
			.stream().map(mapper::toDTO)
			.toList();
	}

    public List<RoomResponseDTO> getAllRoomsForAdmin() {
        return repository.findAllByOrderByCdAsc()
                .stream().map(mapper::toDTO)
                .toList();
    }

	public RoomResponseDTO getRoom(Long roomCd) {
		return mapper.toDTO(repository.findSingleEntity(roomCd));
	}

	public RoomResponseDTO createRoom (RoomRequestDTO roomRequestDTO){
		Room room = repository.save(mapper.toEntity(roomRequestDTO));

		return mapper.toDTO(room);
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
