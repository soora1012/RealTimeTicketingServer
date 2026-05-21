package com.ticketing.seat.dto;


import com.ticketing.seat.domain.Reservation;
import com.ticketing.seat.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class ReservationResponse {

    private final long reservationId;
    private final long seatId;
    private final long concertScheduleId;
    private final ReservationStatus reservationState;
    private final Timestamp reservedAt;

    private final String sectionName;
    private final String rowName;
    private final int seatNumber;
    private final int price;

    private final long concertId;
    private final int concertSequence;

    private final String concertTitle;


    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getReservationPk())
                .seatId(reservation.getSeat().getSeatPk())
                .concertScheduleId(reservation.getConcertSchedule().getConcertSchedulePk())
                .reservationState(reservation.getState())
                .reservedAt(reservation.getReservedAt())
                .sectionName(reservation.getSeat().getSectionName())
                .rowName(reservation.getSeat().getRowName())
                .seatNumber(reservation.getSeat().getSeatNumber())
                .price(reservation.getSeat().getPrice())
                .concertId(reservation.getConcertSchedule().getConcert().getConcertPk())
                .concertSequence(reservation.getConcertSchedule().getSequence())
                .concertTitle(reservation.getConcertSchedule().getConcert().getTitle())
                .build();
    }
}