package com.studioreservation.domain.reservation.service;

import java.security.SecureRandom;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.dto.ReservationRequestDTO;
import com.studioreservation.domain.reservation.dto.StateChangeRequestDTO;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final ReservationMapper reservationMapper;
	@Value("${base62.charset}")
	private String BASE62;
	private static final int MAX_RETRY = 5;


	public PageResponseDTO<ReservationResponseDTO> getAllReservation(PageRequestDTO pageRequestDTO) {
		Specification<ReservationHistory> spec = Specification.where(null);

		if (pageRequestDTO.getStrtDt() != null || pageRequestDTO.getEndDt() != null) {
			spec = spec.and((root, query, cb) -> {
				Path<Timestamp> strtDt = root.get("strtDt");
				Path<Timestamp> endDt = root.get("endDt");

				Predicate p = cb.conjunction();

				if (pageRequestDTO.getEndDt() != null) {
					p = cb.and(p, cb.greaterThanOrEqualTo(endDt, pageRequestDTO.getStrtDt()));
				}
				if (pageRequestDTO.getStrtDt() != null) {
					p = cb.and(p, cb.lessThanOrEqualTo(strtDt, pageRequestDTO.getEndDt()));
				}
				return p;
			});
		}

		Page<ReservationResponseDTO> result = reservationRepository
			.findAll(spec, pageRequestDTO.getPageable(pageRequestDTO.getSortBy()))
			.map(reservationMapper::toDTO);

		return PageResponseDTO.<ReservationResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(pageRequestDTO)
			.total(result.getTotalElements())
			.build();
	}

	public PageResponseDTO<ReservationResponseDTO> getReservationsByRoomCd(Long roomCd, PageRequestDTO pageRequestDTO) {
		Specification<ReservationHistory> spec = Specification.where(null);

		spec = spec.and((root, query, cb) -> {
			Join<ReservationHistory, Room> roomJoin = root.join("room", JoinType.LEFT);
			return cb.equal(roomJoin.get("cd"), roomCd);
		});

		if (pageRequestDTO.getStrtDt() != null || pageRequestDTO.getEndDt() != null) {
			spec = spec.and((root, query, cb) -> {
				Path<Timestamp> strtDt = root.get("strtDt");
				Path<Timestamp> endDt = root.get("endDt");

				Predicate p = cb.conjunction();

				if (pageRequestDTO.getEndDt() != null) {
					p = cb.and(p, cb.greaterThanOrEqualTo(endDt, pageRequestDTO.getStrtDt()));
				}
				if (pageRequestDTO.getStrtDt() != null) {
					p = cb.and(p, cb.lessThanOrEqualTo(strtDt, pageRequestDTO.getEndDt()));
				}
				return p;
			});
		}

		Page<ReservationResponseDTO> result = reservationRepository
			.findAll(spec, pageRequestDTO.getPageable(pageRequestDTO.getSortBy()))
			.map(reservationMapper::toDTO);

		return PageResponseDTO.<ReservationResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(pageRequestDTO)
			.total(result.getTotalElements())
			.build();
	}

	public ReservationResponseDTO getReservation(String phone, String resvCd) {
		return reservationMapper.toDTO(reservationRepository.findReservationHistory(phone, resvCd));
	}
	@Transactional
	public ReservationResponseDTO reserve(Long roomCd, ReservationRequestDTO reservationRequestDTO) {
		ReservationHistory reservationHistory = reservationMapper.toEntity(reservationRequestDTO);
		System.out.println(reservationHistory.getState());
		Room room = roomRepository.findSingleEntity(roomCd);
		reservationHistory.setRoom(room);

		ReservationHistory savedReservationHistory = reservationRepository.save(reservationHistory);
		String resvCd = generateUniqueReservationCode(savedReservationHistory.getSn());
		reservationHistory.setResvCd(resvCd);

		return reservationMapper.toDTO(savedReservationHistory);
	}

	@Transactional
	public ReservationResponseDTO changeState(StateChangeRequestDTO stateChangeRequestDTO) {
		ReservationHistory reservationHistory = reservationRepository
			.findReservationHistory(stateChangeRequestDTO.getPhone(), stateChangeRequestDTO.getResvCd());
		reservationHistory.changeState(stateChangeRequestDTO.getState());

		return reservationMapper.toDTO(reservationHistory);
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
		} while (reservationRepository.existsByResvCd(code));

		return code;
	}
}
