package com.example.hhplus.reservation.api.reservation;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.reservation.dto.ReservationRequest;
import com.example.hhplus.reservation.api.reservation.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @PostMapping
    public ResponseEntity<BasicResponse<ReservationResponse>> createReservation(@RequestHeader String userUuid, @RequestBody ReservationRequest reservationRequest) {
        return null;
    }
}
