package com.ticketing.reservation.domain;

import com.ticketing.concert.domain.ConcertSchedule;
import com.ticketing.seat.domain.Seat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_pk")
    private long reservationPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ReservationStatus state;

    @CreationTimestamp
    @Column(name = "reserved_at", updatable = false)
    private Timestamp reservedAt;

    @Column(name = "member_pk")
    private Long memberPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_pk", nullable = false)
    private ConcertSchedule concertSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_pk", nullable = false)
    private Seat seat;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

}
