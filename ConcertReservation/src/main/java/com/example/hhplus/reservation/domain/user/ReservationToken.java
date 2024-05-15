package com.example.hhplus.reservation.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReservationToken {
    private String tokenValue;
    private Long userId;
    private LocalDateTime issuedAt;
}
