package com.example.hhplus.reservation.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointHistory {
    private Long id;
    private Long userId;
    private TransactionType transactionType;
    private long amount;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public PointHistory(Long id, Long userId, TransactionType transactionType, long amount) {
        this.id = id;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
    }
}
