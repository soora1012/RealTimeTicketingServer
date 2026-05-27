package com.ticketing.queue.service;


import com.ticketing.queue.dto.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class QueueService {


    private final RedisTemplate<String, Object> redisTemplate;
    private static final int ACTIVE_LIMIT = 1;
    private static final int TTL_TIME = 1;
    private static final String QUEUE_KEY = "concert:queue:";
    private static final String SEAT_KEY = "concert:seat:";

    //대기열 등록
    public QueueResponse enterQueue(Long concertScheduleId, Long memberId) {
        String queueKey = QUEUE_KEY + concertScheduleId;
        String queueValue = String.valueOf(memberId);
        boolean check = seatCheck(concertScheduleId, memberId);
        if(check) {
            //중복체크
            Double queueExist = redisTemplate.opsForZSet()
                    .score(queueKey, queueValue);
            if(queueExist == null) {
                redisTemplate.opsForZSet().add(
                        queueKey,
                        queueValue,
                        System.currentTimeMillis());
            }
            Long queueRank = redisTemplate.opsForZSet()
                    .rank(queueKey, queueValue);
            Long queueTotalCount = redisTemplate.opsForZSet()
                    .zCard(queueKey);
            return QueueResponse.of(
                    concertScheduleId,
                    memberId,
                    queueRank,
                    queueTotalCount,
                    true);

        }else {
            //바로 좌석예약 진입
            return QueueResponse.of(
                    concertScheduleId,
                    memberId,
                    false);
        }
    }


    private boolean seatCheck(Long concertScheduleId, Long memberId) {
        String seatKey = SEAT_KEY + concertScheduleId;
        String seatValue = String.valueOf(memberId);


        Long seatTotalCount = redisTemplate.opsForZSet()
                .zCard(seatKey);

        if(seatTotalCount < ACTIVE_LIMIT){
            //seatTotalCount개수가 10 이하면 바로진입
            redisTemplate.opsForZSet().add(
                    seatKey,
                    seatValue,
                    System.currentTimeMillis());
            return false;
        }else {
            Double queueExist = redisTemplate.opsForZSet()
                    .score(seatKey, seatValue);

            if (queueExist == null) {
                //대기열에 없으면
                return true;
            }else{
                //대기열에 있으면
                return false;
            }
        }
    }



    //대기열 떠나기
    public void leaveQueue(Long concertScheduleId, Long memberId) {
        String queueKey = QUEUE_KEY + concertScheduleId;
        String queueValue = String.valueOf(memberId);
        redisTemplate.opsForZSet()
                .remove(queueKey, queueValue);
    }


    //좌석 대기열 진입
    public void enterSeat(Long concertScheduleId, Long memberId) {
        leaveQueue(concertScheduleId, memberId);
        seatCheck(concertScheduleId, memberId);
    }

    //좌석 대기열 떠나기
    public void leaveSeat(Long concertScheduleId, Long memberId) {
        String seatKey = SEAT_KEY + concertScheduleId;
        String seatValue = String.valueOf(memberId);
        redisTemplate.opsForZSet()
                .remove(seatKey, seatValue);
    }


    public Boolean lockSeat(Long seatId, Long memberId) {
        String key = "seat:lock:" + seatId;
        String value = String.valueOf(memberId);
        return redisTemplate.opsForValue()
                .setIfAbsent(key, value, Duration.ofMinutes(TTL_TIME));
    }


    public void unlockSeat(Long seatId) {
        String key = "seat:lock:" + seatId;
        redisTemplate.delete(key);
    }


}
