package com.example.hhplus.reservation.api.user.dto;

import com.example.hhplus.reservation.domain.user.TokenStatus;

public record TokenDetailResponse (
    int waitingCount,
    int rank,
    TokenStatus status
) {

}
