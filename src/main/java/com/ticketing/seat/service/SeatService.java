package com.ticketing.seat.service;

import com.ticketing.seat.dto.SeatResponse;
import com.ticketing.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatList(Long concertScheduleId) {
        List<SeatResponse> seatList =  seatRepository
                                        .findAllByConcertSchedulePk(concertScheduleId)
                                        .stream()
                                        .map(SeatResponse::from)
                                        .toList();

        return seatList;
    }



    @Transactional(readOnly = true)
    public SeatResponse holdSeat(Long seatId) {
        //Redis TTL 사용하기
        //포스타그램 상태 변경
        return null;
    }



    @Transactional(readOnly = true)
    public SeatResponse reservationSeat(Long seatId) {
        //Redis TTL 삭제
        //포스타그램 상태 변경
        return null;
    }



}
