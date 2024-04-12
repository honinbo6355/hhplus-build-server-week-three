package com.example.hhplus.reservation.infrastructure.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}
