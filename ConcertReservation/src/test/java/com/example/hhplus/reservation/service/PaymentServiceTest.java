package com.example.hhplus.reservation.service;

import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import com.example.hhplus.reservation.domain.concert.ConcertDetailRepository;
import com.example.hhplus.reservation.domain.payment.Payment;
import com.example.hhplus.reservation.domain.payment.PaymentRepository;
import com.example.hhplus.reservation.domain.payment.PaymentService;
import com.example.hhplus.reservation.domain.payment.PaymentStatus;
import com.example.hhplus.reservation.domain.reservation.*;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ConcertDetailRepository concertDetailRepository;

    @Mock
    private ReservationTokenRepository reservationTokenRepository;

    @Test
    @DisplayName("결제_성공")
    public void 결제_성공() throws Exception {
        // given
        long reservationId = 10L;
        long concertDetailId = 1L;
        long userId = 1L;
        long point = 15000L;
        long amount = 30000L;
        long seatId = 2L;
        int reservedSeatNum = 20;

        Reservation reservation = new Reservation(reservationId, concertDetailId, seatId, userId, ReservationStatus.IN_PROGRESS, LocalDateTime.now());
        User user = new User(userId, "유저1", amount);
        ConcertDetail concertDetail = new ConcertDetail(concertDetailId, 1L, LocalDateTime.now(), 50, reservedSeatNum);
        ReservationToken reservationToken = new ReservationToken(UUID.randomUUID().toString(), ReservationTokenStatus.IN_PROGRESS, userId, LocalDateTime.now());

        // when
        when(paymentRepository.save(any(Payment.class))).thenReturn(new Payment(reservationId, point, PaymentStatus.SUCCESSED));
        when(reservationRepository.findById(reservationId)).thenReturn(reservation);
        when(seatRepository.findById(reservation.getSeatId())).thenReturn(new Seat(seatId, point));
        when(userRepository.findById(userId)).thenReturn(user);
        when(concertDetailRepository.findByIdWithLock(reservation.getConcertDetailId())).thenReturn(concertDetail);
        when(reservationTokenRepository.findByUserId(userId)).thenReturn(Optional.of(reservationToken));

        paymentService.createPayment(reservationId, userId, point);

        // then
        Assertions.assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
        Assertions.assertThat(user.getAmount()).isEqualTo(amount-point);
        Assertions.assertThat(concertDetail.getReservedSeatNum()).isEqualTo(reservedSeatNum+1);
        Assertions.assertThat(reservationToken.getStatus()).isEqualTo(ReservationTokenStatus.FINISHED);

        verify(reservationRepository, times(1)).save(reservation);
        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
        verify(userRepository, times(1)).save(user);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(concertDetailRepository, times(1)).save(concertDetail);
        verify(reservationTokenRepository, times(1)).save(reservationToken);
    }

    @Test
    @DisplayName("예약한_사용자가_아닌경우_실패")
    public void 예약한_사용자가_아닌경우_실패() throws Exception {
        // given
        long reservationId = 10L;
        long userId = 2L;
        long point = 15000L;
        long seatId = 2L;
        Reservation reservation = new Reservation(1L, 1L, seatId, 1L, ReservationStatus.IN_PROGRESS, LocalDateTime.now());

        // when
        when(reservationRepository.findById(reservationId)).thenReturn(reservation);

        // then
        Assertions.assertThatThrownBy(() -> {
            paymentService.createPayment(reservationId, userId, point);
        }).isInstanceOf(CustomException.class).hasMessage(ErrorCode.INVALID_USER.getMessage());
    }

    @Test
    @DisplayName("좌석_금액과_일치하지않는경우_실패")
    public void 좌석_금액과_일치하지않는경우_실패() throws Exception {
        // given
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;
        long seatId = 2L;
        Reservation reservation = new Reservation(1L, 1L, seatId, userId, ReservationStatus.IN_PROGRESS, LocalDateTime.now());

        // when
        when(reservationRepository.findById(reservationId)).thenReturn(reservation);
        when(seatRepository.findById(reservation.getSeatId())).thenReturn(new Seat(seatId, 25000L));

        // then
        Assertions.assertThatThrownBy(() -> {
            paymentService.createPayment(reservationId, userId, point);
        }).isInstanceOf(CustomException.class).hasMessage(ErrorCode.INVALID_PAYMENT_POINT.getMessage());
    }

    @Test
    @DisplayName("잘못된_예약상태_인경우_실패")
    public void 잘못된_예약상태_인경우_실패() throws Exception {
        // given
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;
        long seatId = 2L;
        Reservation reservation = new Reservation(1L, 1L, seatId, userId, ReservationStatus.CANCELLED, LocalDateTime.now());

        // when
        when(reservationRepository.findById(reservationId)).thenReturn(reservation);
        when(seatRepository.findById(reservation.getSeatId())).thenReturn(new Seat(seatId, point));

        // then
        Assertions.assertThatThrownBy(() -> {
            paymentService.createPayment(reservationId, userId, point);
        }).isInstanceOf(CustomException.class).hasMessage(ErrorCode.INVALID_RESERVATION_STATUS.getMessage());
    }
}
