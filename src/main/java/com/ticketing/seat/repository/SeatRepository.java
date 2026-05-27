package com.ticketing.seat.repository;


import com.ticketing.seat.domain.Seat;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface SeatRepository extends JpaRepository<Seat, Long> {


    @Query("""
                SELECT s
                FROM Seat s
                LEFT JOIN FETCH s.currentReservation r
                WHERE s.concertSchedulePk = :concertSchedulePk
                ORDER BY s.rowName ASC, s.seatNumber ASC
            """)
    List<Seat> findAllWithReservation(Long concertSchedulePk);


    @Modifying
    @Query("""
                UPDATE Seat s
                SET s.currentReservationPk = :currentReservationPk
                WHERE s.seatPk = :seatPk
            """)
    int updateSeatState(@Param("seatPk") Long seatPk,
                        @Param("currentReservationPk") Long currentReservationPk);


    @Query("""
                SELECT s
                FROM Seat s
                LEFT JOIN FETCH s.currentReservation r
                WHERE s.seatPk = :seatPk
            """)
    Optional<Seat> findSeatState(Long seatPk);
}
