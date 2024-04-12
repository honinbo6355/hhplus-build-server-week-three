package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(schema = "test", name = "reservation")
@Getter
public class ReservationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertDetailId;
    private Long userId;
    private Long seatId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
