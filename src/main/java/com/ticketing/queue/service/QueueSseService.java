package com.ticketing.queue.service;

import com.ticketing.queue.dto.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class QueueSseService {

    //SSE 연결유지시간 30분
    private static final Long SSE_TIMEOUT = 1000L * 60 * 30;
    private final QueueService queueService;

    //SSE 연결 저장소
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();


    public SseEmitter subscribe(Long concertScheduleId, Long memberPk) {
        String emitterKey = concertScheduleId + ":" + memberPk;
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitters.put(emitterKey, emitter);

        // 연결 완료/타임아웃/에러 발생 시 메모리에서 제거
        emitter.onCompletion(() -> emitters.remove(emitterKey));
        emitter.onTimeout(() -> emitters.remove(emitterKey));
        emitter.onError((e) -> emitters.remove(emitterKey));

        return emitter;
    }


    //전체 순번이 바뀌므로 모두에게 push
    public void sendQueueStatusToAll(Long concertScheduleId) {
        emitters.forEach((key, emitter) -> {
            if (!key.startsWith(concertScheduleId + ":")) {
                return;
            }
            Long memberPk = Long.valueOf(key.split(":")[1]);
            sendQueueStatus(concertScheduleId, memberPk);
        });
    }



    //특정 1인한테 데이터 전송
    public void sendQueueStatus(Long concertScheduleId, Long memberPk) {
        String emitterKey = concertScheduleId + ":" + memberPk;
        SseEmitter emitter = emitters.get(emitterKey);

        if (emitter == null) {
            return;
        }

        try {
            QueueResponse response = queueService.enterQueue(
                    concertScheduleId,
                    memberPk
            );

            //프론트에 데이터 전송
            emitter.send(
                    SseEmitter.event()
                            .name("queue")
                            .data(response)
            );

        } catch (IOException e) {
            emitters.remove(emitterKey);
        }
    }
}