package com.example.hhplus.reservation.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConcertDetail {
    private Long id;
    private Long concertId;
    private LocalDateTime startsAt;
    private int maxSeatNum;
    private int reservedSeatNum;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public ConcertDetail(Long id, Long concertId, LocalDateTime startsAt, int maxSeatNum, int reservedSeatNum) {
        this.id = id;
        this.concertId = concertId;
        this.startsAt = startsAt;
        this.maxSeatNum = maxSeatNum;
        this.reservedSeatNum = reservedSeatNum;
    }
}
