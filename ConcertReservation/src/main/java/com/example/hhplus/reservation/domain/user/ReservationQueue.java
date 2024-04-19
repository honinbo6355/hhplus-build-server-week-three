package com.example.hhplus.reservation.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationQueue {
    private Long id;
    private ReservationQueueStatus status;
    private Long userId;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public ReservationQueue(ReservationQueueStatus status, Long userId) {
        this.status = status;
        this.userId = userId;
    }

    public void done() {
        this.status = ReservationQueueStatus.DONE;
        this.updatedAt = LocalDateTime.now();
    }
}
