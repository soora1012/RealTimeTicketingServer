package com.ticketing.concert.dto;


import com.ticketing.concert.domain.Concert;
import com.ticketing.concert.domain.ConcertSchedule;
import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
@Builder
public class ConcertScheduleResponse {

    private Long concertId;
    private String title;
    private String venue;
    private String description;

    private Long concertScheduleId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp openDate;
    private String state;

    private int sequence;
    private int totalSeatCount;
    private int reservedSeatCount;
    private int holdSeatCount;
    private int availableSeatCount;

    public static ConcertScheduleResponse from(ConcertSchedule schedule) {
        Concert concert = schedule.getConcert();

        return ConcertScheduleResponse.builder()
                .concertScheduleId(schedule.getConcertSchedulePk())
                .concertId(concert.getConcertPk())
                .title(concert.getTitle())
                .venue(concert.getVenue())
                .sequence(schedule.getSequence())
                .startDate(schedule.getStartAt())
                .endDate(schedule.getEndAt())
                .openDate(schedule.getOpenAt())
                .state(schedule.getState())
                .totalSeatCount(schedule.getTotalSeatCount())
                .reservedSeatCount(schedule.getReservedSeatCount())
                .holdSeatCount(schedule.getHoldSeatCount())
                .availableSeatCount(
                        schedule.getTotalSeatCount()
                                - schedule.getReservedSeatCount()
                                - schedule.getHoldSeatCount()
                )
                .build();
    }
}