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

    //콘서트 좌석 리스트
    @GetMapping("/list/{concertScheduleId}")
    public ApiResponse<List<SeatResponse>> getSeatList(@PathVariable Long concertScheduleId,
                                                       Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        queueService.enterSeat(concertScheduleId, memberId);
        List<SeatResponse> response = seatService.getSeatList(concertScheduleId);
        return ApiResponse.ok(response);
    }

    //콘서트 좌석 queue 삭제
    @DeleteMapping("/leave/{concertScheduleId}")
    public ApiResponse<Void> leaveSeat(@PathVariable Long concertScheduleId,
                                                       Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        queueService.leaveSeat(concertScheduleId, memberId);
        return ApiResponse.ok();
    }
}
