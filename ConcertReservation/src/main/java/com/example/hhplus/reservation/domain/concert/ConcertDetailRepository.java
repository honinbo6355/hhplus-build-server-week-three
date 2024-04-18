package com.example.hhplus.reservation.domain.concert;

import java.util.List;

public interface ConcertDetailRepository {
    List<ConcertDetail> findByConcertId(long concertId);
    ConcertDetail findById(long concertDetailId);
    ConcertDetail save(ConcertDetail concertDetail);
}
