package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationTokenJpaRepository extends JpaRepository<ReservationTokenEntity, Long> {
    Optional<ReservationTokenEntity> findByUserId(long userId);

    @Modifying
    @Query(value = "update ReservationTokenEntity r set r.status = :updateStatus, r.updatedAt = :updatedAt where r.status = :status and r.issuedAt < :targetDateTime")
    int expireDurationOverToken(ReservationTokenStatus updateStatus, ReservationTokenStatus status, LocalDateTime targetDateTime, LocalDateTime updatedAt);

    int countByStatus(ReservationTokenStatus status);

    Optional<ReservationTokenEntity> findByTokenValueAndStatus(String token, ReservationTokenStatus status);
}
