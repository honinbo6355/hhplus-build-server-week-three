package com.example.hhplus.reservation.domain.concert;

import com.example.hhplus.reservation.api.concert.dto.ConcertDateResponse;
import com.example.hhplus.reservation.api.concert.dto.ConcertDetailDto;
import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.domain.reservation.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertDetailRepository concertDetailRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    public ConcertDateResponse getConcertDate(Long concertId) {
        Concert concert = concertRepository.findById(concertId);
        List<ConcertDetail> concertDetails = concertDetailRepository.findByConcertId(concertId);

        return new ConcertDateResponse(concert.getName(), concertDetails.stream()
                .map(concertDetail -> new ConcertDetailDto(concertDetail.getId(), concertDetail.getStartsAt()))
                .collect(Collectors.toList()));
    }

    public List<Long> getConcertSeat(Long concertDetailId) {
        List<Long> seatIds = seatRepository.findIdAll();
        List<Long> reservedSeatIds = reservationRepository.findByConcertDetailIdAndStatusIn(concertDetailId, Arrays.asList(ReservationStatus.IN_PROGRESS, ReservationStatus.COMPLETED));

        return seatIds.stream()
                .filter(seatId -> !reservedSeatIds.contains(seatId))
                .collect(Collectors.toList());
    }
}
