package com.example.hhplus.reservation.service;

import com.example.hhplus.reservation.api.reservation.dto.ReservationResponse;
import com.example.hhplus.reservation.domain.reservation.Reservation;
import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
import com.example.hhplus.reservation.domain.reservation.ReservationService;
import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("좌석_예약_요청_성공할경우")
    public void 좌석_예약_요청_성공할경우() throws Exception {
        // given
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;

        Reservation reservation = new Reservation(concertDetailId, seatId, userId, ReservationStatus.IN_PROGRESS);

        // when
        when(reservationRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId))
                .thenReturn(Optional.empty());
        when(reservationRepository.save(any())).thenReturn(reservation);

        // then
        ReservationResponse reservationResponse = reservationService.createReservation(concertDetailId, userId, seatId);
        Assertions.assertThat(reservationResponse).isEqualTo(ReservationResponse.SUCCESS);
    }

    @Test
    @DisplayName("이미_예약중인경우")
    public void 이미_예약중인경우() throws Exception {
        // given
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;
        Reservation reservation = new Reservation(concertDetailId, seatId, 2L, ReservationStatus.IN_PROGRESS);

        // when
        when(reservationRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId))
                .thenReturn(Optional.ofNullable(reservation));

        // then
        Assertions.assertThatThrownBy(() -> {
            reservationService.createReservation(concertDetailId, userId, seatId);
        }).isInstanceOf(CustomException.class);
    }
}
