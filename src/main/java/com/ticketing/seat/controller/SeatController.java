package com.ticketing.seat.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.queue.service.QueueService;
import com.ticketing.seat.dto.ReservationRequest;
import com.ticketing.seat.dto.ReservationResponse;
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
        Long memberPk = (Long) authentication.getPrincipal();
        queueService.enterSeat(concertScheduleId, memberPk);
        List<SeatResponse> response = seatService.getSeatList(concertScheduleId);
        return ApiResponse.ok(response);
    }

    //좌석예약 홀드
    @GetMapping("/hold/{seatId}")
    public ApiResponse<SeatResponse> holdSeat(@PathVariable Long seatId,
                                                               Authentication authentication) {
        Long memberPk = (Long) authentication.getPrincipal();
        SeatResponse response = seatService.holdSeat(seatId);
        return ApiResponse.ok(response);
    }


    //좌석예약 완료
    @PostMapping("/reservation")
    public ApiResponse<Void> reservationSeat(@RequestBody ReservationRequest reservationRequest,
                                                     Authentication authentication) {
        Long memberPk = (Long) authentication.getPrincipal();
        seatService.reservationSeat(
                reservationRequest.getSeatId(),
                reservationRequest.getConcertScheduleId(),
                memberPk);
        return ApiResponse.ok();
    }


    //좌석예약 정보 
    @GetMapping("/reservationInfo")
    public ApiResponse<List<ReservationResponse>> reservationInfo(Authentication authentication) {
        Long memberPk = (Long) authentication.getPrincipal();
        List<ReservationResponse> response = seatService.reservationInfo(memberPk);
        return ApiResponse.ok(response);
    }


}
