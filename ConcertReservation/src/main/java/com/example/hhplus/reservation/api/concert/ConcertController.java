package com.example.hhplus.reservation.api.concert;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.concert.dto.ConcertDateResponse;
import com.example.hhplus.reservation.api.concert.dto.ConcertSeatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concert")
public class ConcertController {

    @GetMapping("/{concertId}/date")
    public ResponseEntity<BasicResponse<ConcertDateResponse>> getConcertDate(@RequestHeader String userUuid, @PathVariable Long concertId) {
        return null;
    }

    @GetMapping("{concertDetailId}/seat")
    public ResponseEntity<BasicResponse<ConcertSeatResponse>> getConcertSeat(@RequestHeader String userUuid, @PathVariable Long concertDetailId) {
        return null;
    }
}
