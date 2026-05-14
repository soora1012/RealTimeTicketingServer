package com.ticketing.concert.service;


import com.ticketing.concert.domain.ConcertSchedule;
import com.ticketing.concert.dto.ConcertScheduleResponse;
import com.ticketing.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public List<ConcertScheduleResponse> getConcertList() {
        List<ConcertSchedule> concertSchedule = concertRepository.findAllWithConcert();

        return concertSchedule.stream()
                .map(ConcertScheduleResponse::from)
                .toList();
    }


    @Transactional(readOnly = true)
    public ConcertScheduleResponse getConcertSeat() {
        return null;
    }

    }
