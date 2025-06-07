package com.studioreservation.domain.reservation.service;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.reservation.util.Calculator;
import com.studioreservation.domain.reservation.util.ReservationCodeGenerator;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository repository;
	private final RoomRepository roomRepository;
	private final ReservationMapper mapper;
	private final ReservationCodeGenerator codeGenerator;
	private final Calculator calculator;
	private static final int MAX_RETRY = 5;


	public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO requestDTO) {
		Page<ReservationResponseDTO> result = repository.findPagedEntities(requestDTO, null);

		return PageResponseDTO.<ReservationResponseDTO>withAll()
				.data(result.getContent())
				.pageRequestDTO(requestDTO)
				.total(result.getTotalElements())
				.build();
	}

	public PageResponseDTO<ReservationResponseDTO> getReservationsByRoomCd(PageRequestDTO requestDTO, Long roomCd) {
		Page<ReservationResponseDTO> result = repository.findPagedEntities(requestDTO, roomCd);

		return PageResponseDTO.<ReservationResponseDTO>withAll()
				.data(result.getContent())
				.pageRequestDTO(requestDTO)
				.total(result.getTotalElements())
				.build();
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

		String resvCd = generateUniqueReservationCode(reservationHistory.getSn());
		reservationHistory.calculateTotalAmount(room, calculator);
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

	private String generateUniqueReservationCode(Long sn) {
		int attempts = 0;
		String code;

		do {
			code = codeGenerator.generateReservationCode(sn);
			attempts++;
			if (attempts > MAX_RETRY) {
				throw new RuntimeException("예약 코드 중복으로 인해 생성 실패: 재시도 초과");
			}
		} while (repository.existsByResvCd(code));

		return code;
	}
}
