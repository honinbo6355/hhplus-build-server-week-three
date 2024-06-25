package com.example.hhplus.reservation.infrastructure.push;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PushEvent {
    private Long paymentId;
}
