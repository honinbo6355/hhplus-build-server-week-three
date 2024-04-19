package com.example.hhplus.reservation.domain.user;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReservationQueueRepository {
    Optional<ReservationQueue> findByUserId(long userId);
    ReservationQueue save(ReservationQueue reservationQueue);
    int countByWaitingList(long userId);
    List<ReservationQueue> findByWaitingList(ReservationQueueStatus status, Pageable pageable);
}
