package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueRepository;
import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<ReservationQueue> findByWaitingList(ReservationQueueStatus status, Pageable pageable) {
        return reservationQueueJpaRepository.findByWaitingList(status, pageable)
                .stream().map(ReservationQueueEntity::toDomain)
                .collect(Collectors.toList());
    }
}
