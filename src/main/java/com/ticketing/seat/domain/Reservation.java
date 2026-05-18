package com.ticketing.seat.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "state", length = 20)
    private String state;

    @Column(name = "reserved_at")
    private Timestamp reserved_at;

    @Column(name = "schedule_pk")
    private long schedulePk;

    @Column(name = "seat_pk")
    private long seatPk;

    @Column(name = "membeer_pk")
    private long membeerPk;
}
