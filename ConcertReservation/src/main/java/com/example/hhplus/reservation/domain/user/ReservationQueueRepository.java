package com.example.hhplus.reservation.domain.user;

import java.util.Optional;

public interface ReservationQueueRepository {
    Optional<ReservationQueue> findByUserId(long userId);
    ReservationQueue save(ReservationQueue reservationQueue);
    int countByWaitingList(long userId);
}
