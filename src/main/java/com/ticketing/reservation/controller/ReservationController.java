package com.ticketing.reservation.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.queue.service.QueueService;
import com.ticketing.reservation.dto.ReservationHoldResponse;
import com.ticketing.reservation.service.ReservationService;
import com.ticketing.reservation.dto.ReservationRequest;
import com.ticketing.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final QueueService queueService;


    //좌석예약 홀드
    @PutMapping("/hold")
    public ApiResponse<ReservationHoldResponse> hold(@RequestBody ReservationRequest reservationRequest,
                                                  Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        Long seatId = reservationRequest.getSeatId();
        Long concertScheduleId = reservationRequest.getConcertScheduleId();
        if(!queueService.lockSeat(seatId, memberId)){
            return ApiResponse.ok(ReservationHoldResponse.builder()
                    .isHold(false).build());
        }
        ReservationHoldResponse response = reservationService.hold(seatId, concertScheduleId, memberId);
        return ApiResponse.ok(response);
    }


    //좌석예약 완료
    @PutMapping("/completed")
    public ApiResponse<Void> completed(@RequestBody ReservationRequest reservationRequest,
                                             Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        Long seatId = reservationRequest.getSeatId();
        Long concertScheduleId = reservationRequest.getConcertScheduleId();
        queueService.unlockSeat(seatId);
        reservationService.completed(seatId, concertScheduleId, memberId);
        return ApiResponse.ok();
    }



    //예약을 완료하지 못함
    @DeleteMapping("/leave")
    public ApiResponse<Void> leave(@RequestBody ReservationRequest reservationRequest,
                                   Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        Long seatId = reservationRequest.getSeatId();
        Long concertScheduleId = reservationRequest.getConcertScheduleId();
        queueService.unlockSeat(seatId);
        reservationService.leave(seatId, concertScheduleId, memberId);

        return ApiResponse.ok();
    }


    //좌석예약 정보 
    @GetMapping("/info")
    public ApiResponse<List<ReservationResponse>> info(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        List<ReservationResponse> response = reservationService.info(memberId);
        return ApiResponse.ok(response);
    }


}
