package com.ticketing.concert.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "concert")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_pk")
    private long concertPk;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "venue", length = 100)
    private String venue;

    @Column(name = "description")
    private String description;

}
