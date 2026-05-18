package com.ticketing.queue.service;


import com.ticketing.queue.dto.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class QueueService {


    private final RedisTemplate<String, Object> redisTemplate;
    private static final int ACTIVE_LIMIT = 1;
    private static final String QUEUE_KEY = "concert:queue:";
    private static final String SEAT_KEY = "concert:seat:";

    //대기열 등록
    public QueueResponse enterQueue(Long concertScheduleId, Long loginId) {
        String queueKey = QUEUE_KEY + concertScheduleId;
        String queueValue = String.valueOf(loginId);
        boolean check = seatCheck(concertScheduleId, loginId);
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
                    loginId,
                    queueRank,
                    queueTotalCount,
                    true);

        }else {
            //바로 좌석예약 진입
            return QueueResponse.of(
                    concertScheduleId,
                    loginId,
                    false);
        }
    }


    private boolean seatCheck(Long concertScheduleId, Long loginId) {
        String seatKey = SEAT_KEY + concertScheduleId;
        String seatValue = String.valueOf(loginId);


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
    public void leaveQueue(Long concertScheduleId, Long loginId) {
        String queueKey = QUEUE_KEY + concertScheduleId;
        String queueValue = String.valueOf(loginId);
        redisTemplate.opsForZSet()
                .remove(queueKey, queueValue);
    }



    public void enterSeat(Long concertScheduleId, Long loginId) {
        leaveQueue(concertScheduleId, loginId);
        seatCheck(concertScheduleId, loginId);
    }


}
