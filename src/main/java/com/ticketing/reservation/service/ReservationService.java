package com.ticketing.reservation.service;

import com.ticketing.concert.domain.ConcertSchedule;
import com.ticketing.global.error.ApiException;
import com.ticketing.global.error.ErrorCode;
import com.ticketing.member.dto.MemberResponse;
import com.ticketing.reservation.domain.Reservation;
import com.ticketing.reservation.domain.ReservationStatus;
import com.ticketing.reservation.dto.ReservationHoldResponse;
import com.ticketing.reservation.repository.ReservationRepository;
import com.ticketing.seat.domain.Seat;
import com.ticketing.reservation.dto.ReservationResponse;
import com.ticketing.seat.dto.SeatResponse;
import com.ticketing.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;


    @Transactional
    public ReservationHoldResponse hold(Long seatId, Long concertScheduleId, Long memberId) {
        Reservation reservation = Reservation.builder()
                .memberPk(memberId)
                .concertSchedule(ConcertSchedule.builder().concertSchedulePk(concertScheduleId).build())
                .seat(Seat.builder().seatPk(seatId).build())
                .state(ReservationStatus.HOLD)
                .build();
        reservationRepository.save(reservation);
        seatRepository.updateSeatState(seatId, reservation.getReservationPk());

        return ReservationHoldResponse.builder()
                .isHold(true).build();
    }


    //좌석예약 완료
    @Transactional
    public void completed(Long seatId, Long concertScheduleId, Long memberId) {
        Reservation reservation = Reservation.builder()
                .memberPk(memberId)
                .state(ReservationStatus.RESERVED)
                .seat(Seat.builder().seatPk(seatId).build())
                .concertSchedule(ConcertSchedule.builder().concertSchedulePk(concertScheduleId).build())
                .build();
        reservationRepository.save(reservation);
        seatRepository.updateSeatState(seatId, reservation.getReservationPk());

    }


    //예약을 완료하지 못함
    @Transactional
    public void leave(Long seatId, Long concertScheduleId, Long memberId) {
        SeatResponse seatResponse = seatRepository
                                    .findSeatState(seatId)
                                    .map(SeatResponse::from)
                                    .orElseThrow(() ->  new ApiException(ErrorCode.NO_DATA));

        if(seatResponse.getState().equals(ReservationStatus.HOLD)){
            Reservation reservation = Reservation.builder()
                    .memberPk(memberId)
                    .state(ReservationStatus.AVAILABLE)
                    .seat(Seat.builder().seatPk(seatId).build())
                    .concertSchedule(ConcertSchedule.builder().concertSchedulePk(concertScheduleId).build())
                    .build();
            reservationRepository.save(reservation);
            seatRepository.updateSeatState(seatId, reservation.getReservationPk());

        }
    }



    //좌석예약 정보
    @Transactional(readOnly = true)
    public List<ReservationResponse> info(Long memberId) {
        return reservationRepository
                .findAllReservationList(memberId)
                .stream()
                .map(ReservationResponse::from)
                .toList();

    }

}
