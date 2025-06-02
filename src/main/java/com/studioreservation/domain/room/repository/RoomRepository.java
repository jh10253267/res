package com.studioreservation.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	default Room findSingleEntity(Long cd) {
		return findById(cd).orElseThrow();
	}
	List<Room> findAllByUseYnTrueOOrderByCdDesc();
}
