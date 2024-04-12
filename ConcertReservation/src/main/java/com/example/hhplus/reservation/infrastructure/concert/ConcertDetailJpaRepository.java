package com.example.hhplus.reservation.infrastructure.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertDetailJpaRepository extends JpaRepository<ConcertDetailEntity, Long> {
    List<ConcertDetailEntity> findByConcertIdOrderByStartsAtAsc(long concertId);
}
