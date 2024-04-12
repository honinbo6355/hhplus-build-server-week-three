package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.reservation.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Long> findIdAll() {
        return seatJpaRepository.findIdAll();
    }
}
