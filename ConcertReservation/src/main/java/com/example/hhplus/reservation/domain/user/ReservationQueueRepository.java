package com.example.hhplus.reservation.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;
import java.util.Set;

public interface ReservationQueueRepository {
    ReservationQueue save(ReservationQueue reservationQueue) throws JsonProcessingException;
    ReservationQueue remove(ReservationQueue reservationQueue) throws JsonProcessingException;
    Optional<ReservationQueue> findByUserId(long userId) throws JsonProcessingException;
    long countByWaitingList(ReservationQueue reservationQueue) throws JsonProcessingException;
    Set<ReservationQueue> findRange(long startIdx, long endIdx);
    long removeRange(long startIdx, long endIdx);
}
