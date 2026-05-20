package com.ticketing.seat.repository;


import com.ticketing.seat.domain.Reservation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
    SELECT r
    FROM Reservation r
    LEFT JOIN FETCH r.seat s
    LEFT JOIN FETCH r.concertSchedule cs
    LEFT JOIN FETCH cs.concert c
    WHERE r.memberPk = :memberPk
    ORDER BY r.createdAt desc
""")
    List<Reservation> findAllReservationList(@Param("memberPk") long memberPk);

}