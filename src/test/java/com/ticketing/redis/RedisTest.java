package com.ticketing.redis;

import com.ticketing.Application;
import com.ticketing.queue.dto.QueueResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@SpringBootTest(classes = Application.class)
class RedisTest {
    private static final int ACTIVE_LIMIT = 1;
    private static final String QUEUE_KEY = "concert:queue:";
    private static final String SEAT_KEY = "concert:seat:";
    private String queueKey = QUEUE_KEY + 13;
    private String seatKey = SEAT_KEY + 13;
    private String userId = String.valueOf(2);


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {

        //String TTL 1분
        redisTemplate.opsForValue()
                .set(queueKey, "ALLOW", Duration.ofMinutes(1));

        Object result = redisTemplate.opsForValue().get(queueKey);

        System.out.println("Redis result = " + result);
    }

    @Test
    void test1() {
        Object result = redisTemplate.opsForValue().get(queueKey);
        System.out.println("Redis result = " + result);
    }


    @Test
    public void enterQueue() {

        Long concertScheduleId = 13L;
        Long loginId = 1L;

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
            QueueResponse.of(
                    concertScheduleId,
                    loginId,
                    queueRank,
                    queueTotalCount,
                    true);

        }else {
            //바로 좌석예약 진입
            QueueResponse.of(
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


    @Test
    void enterSeat() {
    }
}