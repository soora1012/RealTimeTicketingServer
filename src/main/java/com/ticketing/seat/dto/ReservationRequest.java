package com.ticketing.seat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {

    private long seatId;
    private long concertScheduleId;
}