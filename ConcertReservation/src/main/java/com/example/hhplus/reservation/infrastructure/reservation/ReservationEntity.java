package com.example.hhplus.reservation.infrastructure.reservation;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.reservation.Reservation;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "test", name = "reservation",
        uniqueConstraints = {
            @UniqueConstraint(name = "reservation_unique_key",
                    columnNames = {"concertDetailId", "seatId"})
        }
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertDetailId;
    private Long seatId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation toDomain() {
        return new Reservation(id, concertDetailId, seatId, userId, status, getCreatedAt(), getUpdatedAt());
    }

    public static ReservationEntity toEntity(Reservation reservation) {
        ReservationEntity reservationEntity = new ReservationEntity(reservation.getId(), reservation.getConcertDetailId(),
                reservation.getSeatId(), reservation.getUserId(), reservation.getStatus());
        return reservationEntity;
    }
}
