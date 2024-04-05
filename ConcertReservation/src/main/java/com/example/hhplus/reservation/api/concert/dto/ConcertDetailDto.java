package com.example.hhplus.reservation.api.concert.dto;

import java.time.LocalDateTime;

public record ConcertDetailDto(
    long concertDetailId,
    LocalDateTime startsAt
) {

}
