package com.ticketing.concert.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "concert_schedule")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_schedule_pk")
    private long concertSchedulePk;

    @Column(name = "sequence")
    private int sequence;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @Column(name = "open_at")
    private Timestamp openAt;

    @Column(name = "state", length = 20)
    private String state;

    @Builder.Default
    @Column(name = "total_seat_count")
    private int totalSeatCount = 0;

    @Builder.Default
    @Column(name = "reserved_seat_count")
    private int reservedSeatCount = 0;

    @Builder.Default
    @Column(name = "hold_seat_count")
    private int holdSeatCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_pk", nullable = false)
    private Concert concert;

}
