package com.ticketing.seat.domain;


import com.ticketing.reservation.domain.Reservation;
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
    private int seatNumber;

    @Builder.Default
    @Column(name = "price")
    private int price = 0;

    @Column(name = "concert_schedule_pk")
    private Long concertSchedulePk;

    @Column(name = "current_reservation_pk")
    private Long currentReservationPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "current_reservation_pk",
            referencedColumnName = "reservation_pk",
            insertable = false,
            updatable = false
    )
    private Reservation currentReservation;
}
