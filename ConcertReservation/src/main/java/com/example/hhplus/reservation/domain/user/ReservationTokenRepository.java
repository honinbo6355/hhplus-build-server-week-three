package com.example.hhplus.reservation.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationTokenRepository {
    ReservationToken save(ReservationToken reservationToken) throws JsonProcessingException;
    ReservationToken remove(ReservationToken reservationToken) throws JsonProcessingException;
    Optional<ReservationToken> findByUserId(long userId) throws JsonProcessingException;
    Optional<ReservationToken> findByToken(String token) throws JsonProcessingException;
    long expireDurationOverToken() throws JsonProcessingException;
    long countToken();
}
