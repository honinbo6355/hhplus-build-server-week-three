package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.PointHistory;
import com.example.hhplus.reservation.domain.user.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(PointHistoryEntity.toEntity(pointHistory)).toDomain();
    }
}
