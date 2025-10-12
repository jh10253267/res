package com.studioreservation.domain.room.mapper;

import com.studioreservation.domain.room.dto.RoomAdminResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.studioreservation.domain.room.dto.RoomResponseDTO;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	RoomResponseDTO toDTO(Room room);
    RoomAdminResponseDTO toAdminDTO(Room room);
	Room toEntity(RoomRequestDTO roomRequestDTO);
}
