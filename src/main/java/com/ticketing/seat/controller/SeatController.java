package com.ticketing.seat.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.queue.service.QueueService;
import com.ticketing.seat.dto.SeatResponse;
import com.ticketing.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;
    private final QueueService queueService;

    @GetMapping("/list/{concertScheduleId}")
    public ApiResponse<List<SeatResponse>> getSeatList(@PathVariable Long concertScheduleId,
                                                       Authentication authentication) {
        Long loginId = (Long) authentication.getPrincipal();
        queueService.enterSeat(concertScheduleId, loginId);
        List<SeatResponse> response = seatService.getSeatList(concertScheduleId);
        return ApiResponse.ok(response);
    }


    @GetMapping("/hold/{seatId}")
    public ApiResponse<SeatResponse> holdSeat(@PathVariable Long seatId,
                                                               Authentication authentication) {
        Long loginId = (Long) authentication.getPrincipal();
        SeatResponse response = seatService.holdSeat(seatId);
        return ApiResponse.ok(response);
    }


    @GetMapping("/reservation/{seatId}")
    public ApiResponse<SeatResponse> reservationSeat(@PathVariable Long seatId,
                                              Authentication authentication) {
        Long loginId = (Long) authentication.getPrincipal();
        SeatResponse response = seatService.reservationSeat(seatId);
        return ApiResponse.ok(response);
    }


}
