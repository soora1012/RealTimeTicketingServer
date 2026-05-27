package com.ticketing.reservation.repository;


import com.ticketing.reservation.domain.Reservation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
    SELECT r
    FROM Reservation r
    JOIN r.seat s
    WHERE r.memberPk = :memberPk
    AND r.state IN ('RESERVED', 'CANCELLED')
    ORDER BY r.createdAt DESC
""")
    List<Reservation> findAllReservationList(@Param("memberPk") Long memberPk);

}