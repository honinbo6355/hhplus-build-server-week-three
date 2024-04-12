package com.example.hhplus.reservation.domain.reservation;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Reservation {
    private Long id;
    private Long concertDetailId;
    private Long seatId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Reservation(Long concertDetailId, Long seatId, Long userId, ReservationStatus status) {
        this.concertDetailId = concertDetailId;
        this.seatId = seatId;
        this.userId = userId;
        this.status = status;
    }

    public boolean isReserved() {
        return ReservationStatus.IN_PROGRESS == status || ReservationStatus.COMPLETED == status;
    }
}
