package com.ticketing.seat.dto;


import com.ticketing.reservation.domain.ReservationStatus;
import com.ticketing.seat.domain.Seat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatResponse {

    private final long seatId;
    private final long concertScheduleId;
    private final String sectionName;
    private final String rowName;
    private final int seatNumber;
    private final ReservationStatus state;
    private final int price;


    public static SeatResponse from(Seat seat) {
        return SeatResponse.builder()
                .seatId(seat.getSeatPk())
                .concertScheduleId(seat.getConcertSchedulePk())
                .sectionName(seat.getSectionName())
                .rowName(seat.getRowName())
                .seatNumber(seat.getSeatNumber())
                .state( seat.getCurrentReservation() != null
                        ? seat.getCurrentReservation().getState()
                        : ReservationStatus.AVAILABLE)
                .price(seat.getPrice())
                .build();
    }
}