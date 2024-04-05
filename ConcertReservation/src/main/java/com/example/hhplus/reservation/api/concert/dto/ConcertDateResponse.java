package com.example.hhplus.reservation.api.concert.dto;

import java.util.List;

public record ConcertDateResponse (
    String concertName,
    List<ConcertDetailDto> concertInfos
) {

}
