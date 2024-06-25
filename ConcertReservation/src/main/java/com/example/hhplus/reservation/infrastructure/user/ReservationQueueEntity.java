package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
