package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(schema = "test", name = "reservation_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "reservation_token_unique_key",
                        columnNames = {"userId"})
        })
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTokenEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenValue;

    @Enumerated(EnumType.STRING)
    private ReservationTokenStatus status;

    private Long userId;
    private LocalDateTime issuedAt;

    public ReservationToken toDomain() {
        return new ReservationToken(id, tokenValue, status, userId, issuedAt, getCreatedAt(), getUpdatedAt());
    }

    public static ReservationTokenEntity toEntity(ReservationToken reservationToken) {
        return new ReservationTokenEntity(reservationToken.getId(), reservationToken.getTokenValue(),
                reservationToken.getStatus(), reservationToken.getUserId(), reservationToken.getIssuedAt());
    }
}
