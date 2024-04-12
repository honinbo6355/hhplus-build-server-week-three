package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
    @Query(value = "select a.seatId from ReservationEntity a " +
            "where a.concertDetailId = :concertDetailId and a.status in (:reservationStatusList)")
    List<Long> findSeatIdByConcertDetailIdAndStatusIn(long concertDetailId, List<ReservationStatus> reservationStatusList);
}
