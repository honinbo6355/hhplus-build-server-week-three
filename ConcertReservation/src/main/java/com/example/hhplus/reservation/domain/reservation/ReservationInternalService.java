package com.example.hhplus.reservation.domain.reservation;

import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationInternalService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long reserve(long concertDetailId, long userId, long seatId) {
        Reservation reservation = reservationRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId)
                .orElse(null);
        if (reservation == null) {
            reservation = reservationRepository.save(new Reservation(null, concertDetailId, seatId, userId, ReservationStatus.IN_PROGRESS, LocalDateTime.now()));
        } else if (reservation.isReserved()) {
            throw new CustomException(ErrorCode.ALREADY_RESERVED);
        } else if (ReservationStatus.CANCELLED == reservation.getStatus()) {
            reservation.setUserId(userId);
            reservation.setStatus(ReservationStatus.IN_PROGRESS);
            reservation.setReserveAt(LocalDateTime.now());
            reservationRepository.save(reservation);
        }
        return reservation.getId();
    }
}
