package com.ticketing.queue.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.queue.dto.QueueResponse;
import com.ticketing.queue.service.QueueService;
import com.ticketing.queue.service.QueueSseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;
    private final QueueSseService queueSseService;

    @PostMapping("/enter/{concertScheduleId}")
    public ApiResponse<QueueResponse> enterQueue(@PathVariable Long concertScheduleId,
                                                 Authentication authentication) {

        Long loginId = (Long) authentication.getPrincipal();
        QueueResponse queueResponse = queueService.enterQueue(
                concertScheduleId,
                loginId
        );
        return ApiResponse.ok(queueResponse);
    }


    @DeleteMapping("/leave/{concertScheduleId}")
    public ApiResponse<Void> leaveQueue(@PathVariable Long concertScheduleId,
                                     Authentication authentication) {

        Long loginId = (Long) authentication.getPrincipal();
        queueService.leaveQueue(
                concertScheduleId,
                loginId
        );

        queueSseService.sendQueueStatusToAll(concertScheduleId);
        return ApiResponse.ok();
    }


    //SSE 구독 등록
    @GetMapping(value = "/subscribe/{concertScheduleId}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long concertScheduleId,
                                Authentication authentication) {
        Long loginId = (Long) authentication.getPrincipal();
        return queueSseService.subscribe(concertScheduleId, loginId);
    }
}
