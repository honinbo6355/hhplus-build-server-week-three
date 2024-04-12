package com.example.hhplus.reservation.infrastructure.concert;

import com.example.hhplus.reservation.domain.concert.Concert;
import com.example.hhplus.reservation.domain.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Concert findById(long concertId) {
        ConcertEntity concertEntity = concertJpaRepository.findById(concertId)
                .orElseThrow(NullPointerException::new);
        return concertEntity.toDomain();
    }
}
