package com.studioreservation.domain.room.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.studioreservation.domain.room.dto.RoomResponseDTO;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
	RoomResponseDTO toRoomDTO(Room room);
	Room toRoom(RoomRequestDTO roomRequestDTO);
}
