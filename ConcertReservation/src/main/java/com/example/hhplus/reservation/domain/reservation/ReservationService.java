package com.example.hhplus.reservation.domain.reservation;

import com.example.hhplus.reservation.api.reservation.dto.ReservationResponse;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse createReservation(long concertDetailId, long userId, long seatId) {
        Reservation reservation = reservationRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId)
                .orElse(null);
        if (reservation == null) {
            reservationRepository.save(new Reservation(concertDetailId, seatId, userId, ReservationStatus.IN_PROGRESS));
        } else if (reservation.isReserved()) {
            throw new CustomException(ErrorCode.ALREADY_RESERVED);
        } else if (ReservationStatus.CANCELLED == reservation.getStatus()) {
            reservation.setUserId(userId);
            reservation.setStatus(ReservationStatus.IN_PROGRESS);
            reservationRepository.save(reservation);
        }
        return ReservationResponse.SUCCESS;
    }
}
