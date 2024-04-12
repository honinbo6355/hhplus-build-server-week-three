package com.example.hhplus.reservation.service;

import com.example.hhplus.reservation.api.concert.dto.ConcertDateResponse;
import com.example.hhplus.reservation.domain.concert.*;
import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.domain.reservation.Seat;
import com.example.hhplus.reservation.domain.reservation.SeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertDetailRepository concertDetailRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약_가능_날짜_조회")
    public void 예약_가능_날짜_조회() throws Exception {
        // given
        Long concertId = 1L;
        Concert concert = new Concert(1L, "아이유 콘서트");
        List<ConcertDetail> concertDetails = new ArrayList<>();
        concertDetails.add(new ConcertDetail(1L, 1L, LocalDateTime.of(2024, 2, 15, 13, 0), 50, 10));
        concertDetails.add(new ConcertDetail(1L, 1L, LocalDateTime.of(2024, 2, 20, 15, 0), 50, 10));
        concertDetails.add(new ConcertDetail(1L, 1L, LocalDateTime.of(2024, 3, 15, 13, 0), 50, 10));
        concertDetails.add(new ConcertDetail(1L, 1L, LocalDateTime.of(2024, 3, 20, 15, 0), 50, 10));

        // when
        when(concertRepository.findById(concertId)).thenReturn(concert);
        when(concertDetailRepository.findByConcertId(concertId)).thenReturn(concertDetails);

        // then
        ConcertDateResponse concertDateResponse = concertService.getConcertDate(concertId);

        Assertions.assertThat(concertDateResponse.concertName()).isEqualTo(concert.getName());
        Assertions.assertThat(concertDateResponse.concertInfos().size()).isEqualTo(concertDetails.size());
    }

    @Test
    @DisplayName("예약_가능_좌석_조회")
    public void 예약_가능_좌석_조회() throws Exception {
        // given
        int num = 50;
        Long concertDetailId = 1L;
        List<Long> seatIds = new ArrayList<>();
        List<Long> reservedSeatIds = new ArrayList<>();

        for (long i=1; i<=num; i++) {
            seatIds.add(i);
        }

        reservedSeatIds.add(3L);
        reservedSeatIds.add(5L);
        reservedSeatIds.add(7L);

        // when
        when(seatRepository.findIdAll()).thenReturn(seatIds);
        when(reservationRepository.findByConcertDetailIdAndStatusIn(concertDetailId, Arrays.asList(ReservationStatus.IN_PROGRESS, ReservationStatus.COMPLETED))).thenReturn(reservedSeatIds);

        // then
        List<Long> resultSeatIds = concertService.getConcertSeat(concertDetailId);

        Assertions.assertThat(resultSeatIds).isEqualTo(seatIds.stream().filter(seatId -> !reservedSeatIds.contains(seatId)).collect(Collectors.toList()));
    }
}
