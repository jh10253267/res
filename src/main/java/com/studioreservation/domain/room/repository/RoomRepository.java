package com.studioreservation.domain.room.repository;

import java.util.List;

import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	default Room findSingleEntity(Long cd) {
		return findById(cd).orElseThrow(() ->
				new StudioException(ErrorCode.USER_NOT_FOUND));
	}
	List<Room> findAllByUseYnTrueOrderByCdDesc();
}
