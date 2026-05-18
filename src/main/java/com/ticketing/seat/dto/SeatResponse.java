package com.ticketing.seat.dto;


import com.ticketing.seat.domain.Seat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatResponse {

    private final String loginId;
    private final String token;
    private final int passwordResetCount;

    public static SeatResponse from(Seat seat) {
        return SeatResponse.builder()
                .build();
    }
}