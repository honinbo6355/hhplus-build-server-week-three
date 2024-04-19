package com.example.hhplus.reservation.domain.user;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationTokenRepository {
    Optional<ReservationToken> findByUserId(long userId);
    ReservationToken save(ReservationToken reservationToken);
    int expireDurationOverToken(ReservationTokenStatus updateStatus, ReservationTokenStatus status, LocalDateTime targetDateTime, LocalDateTime updatedAt);
    int countByStatus(ReservationTokenStatus status);
    Optional<ReservationToken> findByTokenValueAndStatus(String token, ReservationTokenStatus status);
}
