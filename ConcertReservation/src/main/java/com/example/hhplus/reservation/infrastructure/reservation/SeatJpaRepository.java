package com.example.hhplus.reservation.infrastructure.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {
    @Query(value = "select a.id from SeatEntity a")
    List<Long> findIdAll();
}
