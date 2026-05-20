package com.ticketing.seat.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_pk")
    private long seatPk;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "row_name")
    private String rowName;

    @Column(name = "seat_number")
    private String seatNumber;

    @Builder.Default
    @Column(name = "price")
    private int price = 0;

    @Column(name = "concert_schedule_pk")
    private long concertSchedulePk;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seat_pk",
            referencedColumnName = "seat_pk",
            insertable = false,
            updatable = false
    )
    private Reservation reservation;
}
