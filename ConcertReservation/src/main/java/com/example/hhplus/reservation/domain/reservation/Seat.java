package com.example.hhplus.reservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Seat {
    private Long id;
    private long price;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Seat(Long id, long price) {
        this.id = id;
        this.price = price;
    }
}
