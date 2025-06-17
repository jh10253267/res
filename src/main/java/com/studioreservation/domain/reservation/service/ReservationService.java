package com.studioreservation.domain.reservation.service;

import com.studioreservation.domain.reservation.dto.*;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.reservation.util.Base32CodeGenerator;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository repository;
	private final RoomRepository roomRepository;
	private final ReservationMapper mapper;
	private final Base32CodeGenerator codeGenerator;
	private static final int MAX_RETRY = 5;


	public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO requestDTO) {
		Page<ReservationResponseDTO> result = repository.findPagedEntities(requestDTO);

		return PageResponseDTO.<ReservationResponseDTO>withAll()
				.data(result.getContent())
				.pageRequestDTO(requestDTO)
				.total(result.getTotalElements())
				.build();
	}

	public List<ReservedTimeResDTO> getReservedTimes(ReservedTimeReqDTO reservedTimeReqDTO) {
		return repository.findReservedTime(reservedTimeReqDTO.getStrtDt(), reservedTimeReqDTO.getEndDt());
	}

	public ReservationResponseDTO getReservation(String phone, String resvCd) {
		return mapper.toDTO(repository.findReservationHistory(phone, resvCd));
	}

	@Transactional
	public ReservationResponseDTO reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
		ReservationHistory reservationHistory = mapper.toEntity(reservationRequestDTO);
		Room room = roomRepository.findSingleEntity(roomCd);
		reservationHistory.setRoom(room);
		repository.save(reservationHistory);

		String resvCd = generateUniqueReservationCode(reservationHistory.getCreatedAt());
		reservationHistory.calculateTotalAmount(room);
		reservationHistory.setResvCd(resvCd);

		return mapper.toDTO(reservationHistory);
	}

	@Transactional
	public ReservationResponseDTO updateReservation(ReservationChangeRequestDTO requestDTO,
													String phone,
													String resvCd) {
		ReservationHistory reservationHistory = repository
				.findReservationHistory(phone, resvCd);
		reservationHistory.updateReservation(requestDTO);

		return mapper.toDTO(reservationHistory);
	}

	public Integer getTotalAmount(ReservationAmoutDTO amountDTO) {
		return repository.sumTotalAmount(amountDTO.getStrtDt(),
				amountDTO.getEndDt());
	}

	public ReservationStateResponse getCountOfReservations(ReservedTimeReqDTO reservedTimeReqDTO) {
		return repository.findCountByState(reservedTimeReqDTO.getStrtDt(),
				reservedTimeReqDTO.getEndDt());
	}

	private String generateUniqueReservationCode(Timestamp date) {
		int attempts = 0;
		String code;

		do {
			code = codeGenerator.generateCodeWithDate(date);
			attempts++;
			if (attempts > MAX_RETRY) {
				throw new RuntimeException("예약 코드 중복으로 인해 생성 실패: 재시도 초과");
			}
		} while (repository.existsByResvCd(code));

		return code;
	}
}
