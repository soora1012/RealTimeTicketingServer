package com.ticketing.seat.service;

import com.ticketing.concert.domain.ConcertSchedule;
import com.ticketing.seat.domain.Reservation;
import com.ticketing.seat.domain.ReservationStatus;
import com.ticketing.seat.domain.Seat;
import com.ticketing.seat.dto.ReservationResponse;
import com.ticketing.seat.dto.SeatResponse;
import com.ticketing.seat.repository.ReservationRepository;
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

    @Transactional
    public SeatResponse holdSeat(Long seatId) {
        //Redis TTL 사용하기
        //포스타그램 상태 변경
        return null;
    }


    //좌석예약 완료
    @Transactional
    public void reservationSeat(Long seatId, Long concertScheduleId, Long loginId) {
        //Redis TTL 삭제
        Reservation reservation = Reservation.builder()
                .memberPk(loginId)
                .state(ReservationStatus.RESERVED)
                .seat(Seat.builder().seatPk(seatId).build())
                .concertSchedule(ConcertSchedule.builder().concertSchedulePk(concertScheduleId).build())
                .build();

        reservationRepository.save(reservation);
    }



    //좌석예약 정보
    @Transactional(readOnly = true)
    public List<ReservationResponse> reservationInfo(Long loginId) {
        return reservationRepository
                .findAllReservationList(loginId)
                .stream()
                .map(ReservationResponse::from)
                .toList();

    }




}
