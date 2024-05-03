package com.example.hhplus.reservation.domain.reservation;

import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public Long createReservation(long concertDetailId, long userId, long seatId) {
        RLock rLock = redissonClient.getLock(String.format("reservation-%d-%d", concertDetailId, seatId));

        try {
            boolean isLock = rLock.tryLock(5L, 3L, TimeUnit.SECONDS);
            if (!isLock) {
                throw new CustomException(ErrorCode.LOCK_NOT_AVAILABLE);
            }

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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
            log.info("unlock complete: {}", rLock.getName());
        }
    }
}
