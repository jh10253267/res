package com.studioreservation.domain.csv.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.studioreservation.domain.platform.entity.Platform;
import com.studioreservation.domain.platform.repository.PlatformRepository;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.repository.RoomRepository;
import com.studioreservation.global.formatter.TimestampFromString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final RoomRepository roomRepository;
    private final PlatformRepository platformRepository;
    private final ReservationRepository reservationRepository;

    public void csvBulkUpload(MultipartFile file) throws IOException {
        List<ReservationHistory> reservationHistories = new ArrayList<>();
        Map<Long, Room> roomMap = roomRepository.findAll().stream()
                .collect(Collectors.toMap(Room::getCd, Function.identity()));
        Map<Long, Platform> platformMap = platformRepository.findAll().stream()
                .collect(Collectors.toMap(Platform::getCd, Function.identity()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            csvReader.readNext();
            while ((line = csvReader.readNext()) != null) {
                ReservationHistory reservationHistory = ReservationHistory.builder()
                        .userNm(line[0])
                        .phone(line[1])
                        .payTyp(PayTyp.fromStr(line[2]))
                        .state(ReservationState.fromStr(line[3]))
                        .strtDt(TimestampFromString.parse(line[4]))
                        .endDt(TimestampFromString.parse(line[5]))
                        .useParking(Boolean.parseBoolean(line[6]))
                        .needTaxInvoce(Boolean.parseBoolean(line[7]))
                        .senderNm(line[8])
                        .proposal(line[9])
                        .requestCont(line[10])
                        .policyConfirmed(Boolean.parseBoolean(line[11]))
                        .memo(line[12])
                        .resvCd(line[13])
                        .room(roomMap.get(Long.parseLong(line[14])))
                        .platform(platformMap.get(Long.parseLong(line[15])))
                        .commission(Integer.parseInt(line[16]))
                        .income(Integer.parseInt(line[17]))
                        .totalRevenue(Integer.parseInt(line[18]))
                        .build();
                reservationHistories.add(reservationHistory);
            }
            reservationRepository.saveAll(reservationHistories);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}