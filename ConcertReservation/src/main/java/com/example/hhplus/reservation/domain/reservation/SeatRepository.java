package com.example.hhplus.reservation.domain.reservation;

import java.util.List;

public interface SeatRepository {
    List<Long> findIdAll();
}
