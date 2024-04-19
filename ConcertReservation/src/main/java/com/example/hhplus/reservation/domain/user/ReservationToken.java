package com.example.hhplus.reservation.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReservationToken {
    private Long id;
    private String tokenValue;
    private ReservationTokenStatus status;
    private Long userId;
    private LocalDateTime issuedAt;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public static final int MAX_IN_PROGRESS_SIZE = 8;
    private static final int TOKEN_DURATION_MINUTE = 5;

    public ReservationToken(String tokenValue, ReservationTokenStatus status, Long userId, LocalDateTime issuedAt) {
        this.tokenValue = tokenValue;
        this.status = status;
        this.userId = userId;
        this.issuedAt = issuedAt;
    }

    public void inProgress() {
        this.tokenValue = UUID.randomUUID().toString();
        this.status = ReservationTokenStatus.IN_PROGRESS;
        this.issuedAt = LocalDateTime.now();
    }
}
