package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.infrastructure.BaseTimeEntity;
import com.example.hhplus.reservation.domain.reservation.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "test", name = "seat")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long price;

    public Seat toDomain() {
        return new Seat(id, price);
    }

    public static SeatEntity toEntity(Seat seat) {
        return new SeatEntity(seat.getId(), seat.getPrice());
    }
}
