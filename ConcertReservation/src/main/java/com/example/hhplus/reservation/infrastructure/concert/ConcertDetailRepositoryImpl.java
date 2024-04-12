package com.example.hhplus.reservation.infrastructure.concert;

import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import com.example.hhplus.reservation.domain.concert.ConcertDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertDetailRepositoryImpl implements ConcertDetailRepository {

    private final ConcertDetailJpaRepository concertDetailJpaRepository;

    @Override
    public List<ConcertDetail> findByConcertId(long concertId) {
        List<ConcertDetailEntity> concertDetailEntityList = concertDetailJpaRepository.findByConcertIdOrderByStartsAtAsc(concertId);

        return concertDetailEntityList.stream()
                .map(ConcertDetailEntity::toDomain)
                .collect(Collectors.toList());
    }
}
