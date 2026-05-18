package com.ticketing.concert.controller;

import com.ticketing.concert.dto.ConcertScheduleResponse;
import com.ticketing.concert.service.ConcertService;
import com.ticketing.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concert")
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping("/list")
    public ApiResponse<List<ConcertScheduleResponse>> getConcertList() {

        List<ConcertScheduleResponse> response = concertService.getConcertList();
        return ApiResponse.ok(response);
    }

}
