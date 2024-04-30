package com.example.hhplus.reservation.infrastructure.concert;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertDetailJpaRepository extends JpaRepository<ConcertDetailEntity, Long> {
    List<ConcertDetailEntity> findByConcertIdOrderByStartsAtAsc(long concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select a from ConcertDetailEntity a where a.id = :id")
    ConcertDetailEntity findByIdWithPessimisticLock(long id);
}
