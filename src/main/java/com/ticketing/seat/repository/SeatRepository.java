package com.ticketing.seat.repository;


import com.ticketing.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByConcertSchedulePk(Long concertScheduleId);
}