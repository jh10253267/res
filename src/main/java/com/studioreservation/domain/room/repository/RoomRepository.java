package com.studioreservation.domain.room.repository;

import java.util.List;
import java.util.Optional;

import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findRoomByName(String roomName);
	void deleteRoomByName(String roomName);
	default Room findSingleEntity(Long cd) {
		return findById(cd).orElseThrow(() ->
				new StudioException(ErrorCode.NO_SUCH_ROOM));
	}

	default Room findSingleEntityByName(String roomName) {
		return findRoomByName(roomName).orElseThrow(() ->
				new StudioException(ErrorCode.NO_SUCH_ROOM));
	}
	List<Room> findAllByUseYnTrueOrderByCdDesc();
}
