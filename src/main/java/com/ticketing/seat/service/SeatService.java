package com.ticketing.seat.service;

import com.ticketing.concert.domain.ConcertSchedule;
import com.ticketing.reservation.domain.Reservation;
import com.ticketing.reservation.domain.ReservationStatus;
import com.ticketing.seat.domain.Seat;
import com.ticketing.reservation.dto.ReservationResponse;
import com.ticketing.seat.dto.SeatResponse;
import com.ticketing.reservation.repository.ReservationRepository;
import com.ticketing.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatList(Long concertScheduleId) {
       return seatRepository
                .findAllWithReservation(concertScheduleId)
                .stream()
                .map(SeatResponse::from)
                .toList();
    }




}
