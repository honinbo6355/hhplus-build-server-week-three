package com.example.hhplus.reservation.api.concert;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.concert.dto.ConcertDateResponse;
import com.example.hhplus.reservation.domain.concert.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concert")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/{concertId}/date")
    public ResponseEntity<BasicResponse<ConcertDateResponse>> getConcertDate(@PathVariable Long concertId) {
        ConcertDateResponse concertDateResponse = concertService.getConcertDate(concertId);
        return new ResponseEntity<>(new BasicResponse<>(concertDateResponse, null), HttpStatus.OK);
    }

    @GetMapping("{concertDetailId}/seat")
    public ResponseEntity<BasicResponse<List<Long>>> getConcertSeat(@PathVariable Long concertDetailId) {
        List<Long> seatIds = concertService.getConcertSeat(concertDetailId);
        return new ResponseEntity<>(new BasicResponse<>(seatIds, null), HttpStatus.OK);
    }
}
