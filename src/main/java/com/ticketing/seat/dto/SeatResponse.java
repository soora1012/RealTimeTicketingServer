package com.ticketing.seat.dto;


import com.ticketing.seat.domain.ReservationStatus;
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
    private final String seatNumber;
    private final int price;
    private final ReservationStatus state;


    public static SeatResponse from(Seat seat) {
        return SeatResponse.builder()
                .seatId(seat.getSeatPk())
                .concertScheduleId(seat.getConcertSchedulePk())
                .sectionName(seat.getSectionName())
                .rowName(seat.getRowName())
                .seatNumber(seat.getSeatNumber())
                .price(seat.getPrice())
                .state(seat.getReservation() != null
                        ? seat.getReservation().getState()
                        : ReservationStatus.AVAILABLE)
                .build();
    }
}