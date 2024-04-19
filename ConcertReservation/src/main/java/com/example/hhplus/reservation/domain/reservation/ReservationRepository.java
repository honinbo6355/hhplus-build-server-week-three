package com.example.hhplus.reservation.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Long> findByConcertDetailIdAndStatusIn(Long concertDetailId, List<ReservationStatus> reservationStatusList);

    Optional<Reservation> findByConcertDetailIdAndSeatId(long concertDetailId, long seatId);

    Reservation save(Reservation reservation);

    Reservation findById(long reservationId);

    int cancelReservation(ReservationStatus updateStatus, ReservationStatus status, LocalDateTime targetDateTime, LocalDateTime updatedAt);
}
