package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(schema = "test", name = "reservation_queue",
        uniqueConstraints = {
                @UniqueConstraint(name = "reservation_queue_unique_key",
                        columnNames = {"userId"})
        })
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationQueueEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationQueueStatus status;

    private Long userId;

    public ReservationQueue toDomain() {
        return new ReservationQueue(id, status, userId, getCreatedAt(), getUpdatedAt());
    }

    public static ReservationQueueEntity toEntity(ReservationQueue reservationQueue) {
        return new ReservationQueueEntity(reservationQueue.getId(), reservationQueue.getStatus(), reservationQueue.getUserId());
    }
}
