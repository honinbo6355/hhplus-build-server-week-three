package com.example.hhplus.reservation.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long reservationId;
    private long amount;
    private PaymentStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
