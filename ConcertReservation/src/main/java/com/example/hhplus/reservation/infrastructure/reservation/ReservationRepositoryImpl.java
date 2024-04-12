package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public List<Long> findByConcertDetailIdAndStatusIn(Long concertDetailId, List<ReservationStatus> reservationStatusList) {
        return reservationJpaRepository.findSeatIdByConcertDetailIdAndStatusIn(concertDetailId, reservationStatusList);
    }
}
