package com.example.hhplus.reservation.domain.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationTokenRepository {
    Optional<ReservationToken> findByUserId(long userId);
    ReservationToken save(ReservationToken reservationToken);
    int countByStatus(ReservationTokenStatus status);
}
