package com.ticketing.concert.repository;

import com.ticketing.concert.domain.Concert;
import com.ticketing.concert.domain.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long> {


    @Query("""
        SELECT cs
        FROM ConcertSchedule cs
        JOIN FETCH cs.concert
        ORDER BY cs.startAt ASC
    """)
    List<ConcertSchedule> findAllWithConcert();

    Optional<Concert> findById(Long concertPk);
}