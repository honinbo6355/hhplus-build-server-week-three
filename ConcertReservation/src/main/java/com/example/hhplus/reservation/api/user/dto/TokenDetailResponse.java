package com.example.hhplus.reservation.api.user.dto;

public record TokenDetailResponse (
    Integer rank,
    String token,
    TokenDetailStatus status
) {

}
