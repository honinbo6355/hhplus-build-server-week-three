package com.example.hhplus.reservation.api.payment.dto;

public record PaymentRequest (
    long reservationId,
    long userId,
    long point
) {

}
