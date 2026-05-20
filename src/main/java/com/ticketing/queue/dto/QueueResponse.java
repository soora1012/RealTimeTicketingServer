package com.ticketing.queue.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class QueueResponse {
    private Long concertScheduleId;
    private Long memberPk;

    // 내 대기번호
    private Long myPosition;
    // 내 앞에 남은 사람 수
    private Long aheadCount;
    // 전체 대기열 수
    private Long totalCount;
    // 입장 가능 여부
    private boolean active;


    public static QueueResponse of(Long concertScheduleId,
                                     Long memberPk,
                                     Long rank,
                                     Long totalCount,
                                     boolean active) {
        return QueueResponse.builder()
                .concertScheduleId(concertScheduleId)
                .memberPk(memberPk)
                .myPosition(rank == null ? null : rank + 1)
                .aheadCount(rank == null ? null : rank)
                .totalCount(totalCount)
                .active(active)
                .build();
    }

    public static QueueResponse of(Long concertScheduleId,
                                   Long memberPk,
                                   boolean active) {
        return QueueResponse.builder()
                .concertScheduleId(concertScheduleId)
                .memberPk(memberPk)
                .myPosition(0L)
                .aheadCount(0L)
                .totalCount(0L)
                .active(active)
                .build();
    }
}
