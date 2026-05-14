package com.ticketing.concert.domain;

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

    @Column(name = "expired_at")
    private Timestamp end_at;

}
