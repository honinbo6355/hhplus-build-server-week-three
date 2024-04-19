package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.reservation.Reservation;
import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public List<Long> findByConcertDetailIdAndStatusIn(Long concertDetailId, List<ReservationStatus> reservationStatusList) {
        return reservationJpaRepository.findSeatIdByConcertDetailIdAndStatusIn(concertDetailId, reservationStatusList);
    }

    @Override
    public Optional<Reservation> findByConcertDetailIdAndSeatId(long concertDetailId, long seatId) {
        return reservationJpaRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId)
                .map(ReservationEntity::toDomain);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationEntity.toEntity(reservation)).toDomain();
    }

    @Override
    public Reservation findById(long reservationId) {
        return reservationJpaRepository.findById(reservationId)
                .map(ReservationEntity::toDomain)
                .orElse(null);
    }

    @Override
    public int cancelReservation(ReservationStatus updateStatus, ReservationStatus status, LocalDateTime targetDateTime, LocalDateTime updatedAt) {
        return reservationJpaRepository.cancelReservation(updateStatus, status, targetDateTime, updatedAt);
    }
}
