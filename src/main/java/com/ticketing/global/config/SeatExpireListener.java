package com.ticketing.global.config;


import com.ticketing.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SeatExpireListener implements MessageListener {
    private final ReservationService reservationService;

    @Override
    @Transactional
    public void onMessage(Message message, byte[] pattern) {

        String expiredKey = message.toString();
        if (!expiredKey.startsWith("seat:lock:")) {
            return;
        }

        String[] split = expiredKey.split(":");
        Long seatId = Long.valueOf(split[2]);
        Long concertScheduleId = Long.valueOf(split[3]);
        Long memberId = Long.valueOf(split[4]);

        reservationService.leave(seatId, concertScheduleId, memberId);
    }
}
