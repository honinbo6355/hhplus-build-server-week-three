package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.reservation.Reservation;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
    @Query(value = "select a.seatId from ReservationEntity a " +
            "where a.concertDetailId = :concertDetailId and a.status in (:reservationStatusList)")
    List<Long> findSeatIdByConcertDetailIdAndStatusIn(long concertDetailId, List<ReservationStatus> reservationStatusList);

    Optional<ReservationEntity> findByConcertDetailIdAndSeatId(long concertDetailId, long seatId);
}
