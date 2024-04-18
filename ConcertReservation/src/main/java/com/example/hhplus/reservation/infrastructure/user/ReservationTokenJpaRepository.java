package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationTokenJpaRepository extends JpaRepository<ReservationTokenEntity, Long> {
    Optional<ReservationTokenEntity> findByUserId(long userId);
    int countByStatus(ReservationTokenStatus status);
}
