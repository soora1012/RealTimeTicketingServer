package com.ticketing.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {

    private Long seatId;
    private Long concertScheduleId;
}