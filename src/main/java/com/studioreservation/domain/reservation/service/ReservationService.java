package com.studioreservation.domain.reservation.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository repository;
	private final RoomRepository roomRepository;
	private final ReservationMapper mapper;
	@Value("${base62.charset}")
	private String BASE62;
	private static final int MAX_RETRY = 5;


	public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO requestDTO) {
		Page<ReservationResponseDTO> result = repository.findPagedEntities(requestDTO, null);
		System.out.println(result);
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

		ReservationHistory savedReservationHistory = repository.save(reservationHistory);
		String resvCd = generateUniqueReservationCode(savedReservationHistory.getSn());
		reservationHistory.setResvCd(resvCd);

		return mapper.toDTO(savedReservationHistory);
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

	private String encodeBase62(long baseSn) {
		StringBuilder sb = new StringBuilder();
		while (baseSn > 0) {
			sb.append(BASE62.charAt((int)(baseSn % 62)));
			baseSn /= 62;
		}
		return sb.reverse().toString();
	}

	private String generateReservationCode(long id) {
		long OFFSET = 1_000_000L;
		String code = encodeBase62(OFFSET + id);
		return padWithRandomChars(code, 6, BASE62);
	}

	private static String padWithRandomChars(String base62Code, int totalLength, String base62Charset) {
		int padLength = totalLength - base62Code.length();
		if (padLength <= 0) return base62Code;

		SecureRandom random = new SecureRandom();
		StringBuilder padding = new StringBuilder();
		for (int i = 0; i < padLength; i++) {
			int index = random.nextInt(62);
			padding.append(base62Charset.charAt(index));
		}

		return padding + base62Code;
	}

	public String generateUniqueReservationCode(long id) {
		int attempts = 0;
		String code;

		do {
			code = generateReservationCode(id);
			attempts++;
			if (attempts > MAX_RETRY) {
				throw new RuntimeException("예약 코드 중복으로 인해 생성 실패: 재시도 초과");
			}
		} while (repository.existsByResvCd(code));

		return code;
	}
}
