package com.example.hhplus.reservation.api.reservation.dto;

public record ReservationRequest (
        long concertDetailId,
        long seatId
) {

}
