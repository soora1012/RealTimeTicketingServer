package com.ticketing.global.config;


import com.ticketing.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SeatExpireListener implements MessageListener {

    @Override
    @Transactional
    public void onMessage(Message message, byte[] pattern) {

        String expiredKey = message.toString();

        if (!expiredKey.startsWith("seat:hold:")) {
            return;
        }

        Long seatId = Long.valueOf(
                expiredKey.replace("seat:hold:", "")
        );

        System.out.println("SeatExpireListener received seatId: " + seatId);
      ///  seatRepository.releaseSeat(seatId);
    }
}
