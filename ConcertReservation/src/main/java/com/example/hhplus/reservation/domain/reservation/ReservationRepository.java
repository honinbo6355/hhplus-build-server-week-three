package com.example.hhplus.reservation.domain.reservation;

import java.util.List;

public interface ReservationRepository {
    List<Long> findByConcertDetailIdAndStatusIn(Long concertDetailId, List<ReservationStatus> reservationStatusList);
}
