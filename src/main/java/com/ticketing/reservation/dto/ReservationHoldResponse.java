package com.ticketing.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationHoldResponse {
    private boolean isHold;
}
