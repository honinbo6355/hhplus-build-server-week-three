package com.example.hhplus.reservation.api.reservation;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.reservation.dto.ReservationRequest;
import com.example.hhplus.reservation.api.reservation.dto.ReservationResponse;
import com.example.hhplus.reservation.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<BasicResponse<ReservationResponse>> createReservation(@RequestBody ReservationRequest reservationRequest) {
        return null;
    }
}
