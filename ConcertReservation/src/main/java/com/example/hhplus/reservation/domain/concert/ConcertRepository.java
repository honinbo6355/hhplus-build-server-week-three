package com.example.hhplus.reservation.domain.concert;

public interface ConcertRepository {
    Concert findById(long concertId);
}
