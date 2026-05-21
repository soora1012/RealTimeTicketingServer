package com.ticketing.seat.repository;


import com.ticketing.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, Long> {


    @Query("""
    SELECT s
    FROM Seat s
    LEFT JOIN FETCH s.reservation
    WHERE s.concertSchedulePk = :concertSchedulePk
    ORDER BY s.rowName ASC, s.seatNumber ASC
""")
    List<Seat> findAllWithReservation(Long concertSchedulePk);

}