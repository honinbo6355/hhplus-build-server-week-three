package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationQueueEntity {
    private Long userId;

    public ReservationQueue toDomain() {
        return new ReservationQueue(userId);
    }

    public static ReservationQueueEntity toEntity(ReservationQueue reservationQueue) {
        return new ReservationQueueEntity(reservationQueue.getUserId());
    }
}
