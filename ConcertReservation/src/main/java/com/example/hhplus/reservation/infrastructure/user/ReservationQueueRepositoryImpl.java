package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationQueueRepositoryImpl implements ReservationQueueRepository {

    private final ReservationQueueJpaRepository reservationQueueJpaRepository;

    @Override
    public Optional<ReservationQueue> findByUserId(long userId) {
        return reservationQueueJpaRepository.findByUserId(userId)
                .map(ReservationQueueEntity::toDomain);
    }

    @Override
    public ReservationQueue save(ReservationQueue reservationQueue) {
        return reservationQueueJpaRepository.save(ReservationQueueEntity.toEntity(reservationQueue))
                .toDomain();
    }

    @Override
    public int countByWaitingList(long userId) {
        return reservationQueueJpaRepository.countByWaitingList(userId);
    }
}
