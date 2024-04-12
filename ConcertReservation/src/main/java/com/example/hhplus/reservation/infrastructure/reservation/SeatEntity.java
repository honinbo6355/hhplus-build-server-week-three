package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(schema = "test", name = "seat")
@Getter
public class SeatEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long price;
}
