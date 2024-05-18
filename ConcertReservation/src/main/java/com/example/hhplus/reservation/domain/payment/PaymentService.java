package com.example.hhplus.reservation.domain.payment;

import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import com.example.hhplus.reservation.domain.concert.ConcertDetailRepository;
import com.example.hhplus.reservation.domain.reservation.*;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import com.example.hhplus.reservation.external.PushClient;
import com.example.hhplus.reservation.infrastructure.push.PushEvent;
import com.example.hhplus.reservation.infrastructure.user.ReservationTokenEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final SeatRepository seatRepository;
    private final ConcertDetailRepository concertDetailRepository;
    private final ReservationTokenRepository reservationTokenRepository;
    private final PushClient pushClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Long createPayment(long reservationId, long userId, long point) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new NullPointerException();
        }
        if (!reservation.isReserveUser(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        Seat seat = seatRepository.findById(reservation.getSeatId());
        if (seat == null) {
            throw new NullPointerException();
        }
        if (point != seat.getPrice()) {
            throw new CustomException(ErrorCode.INVALID_PAYMENT_POINT);
        }
        if (reservation.getStatus() != ReservationStatus.IN_PROGRESS) {
            throw new CustomException(ErrorCode.INVALID_RESERVATION_STATUS);
        }

        User user = userRepository.findByIdWithPessimisticLock(userId);
        if (user == null) {
            throw new NullPointerException();
        }

        ConcertDetail concertDetail = concertDetailRepository.findByIdWithPessimisticLock(reservation.getConcertDetailId());
        if (concertDetail == null) {
            throw new NullPointerException();
        }

        user.use(point);
        userRepository.save(user);
        pointHistoryRepository.save(new PointHistory(null, userId, TransactionType.USE, point));
        Payment payment = paymentRepository.save(new Payment(reservationId, point, PaymentStatus.SUCCESSED));

        reservation.setStatus(ReservationStatus.COMPLETED);
        concertDetail.increaseReservedSeatNum();

        reservationRepository.save(reservation);
        concertDetailRepository.save(concertDetail);

        log.info("createPayment tx : {}", TransactionSynchronizationManager.isActualTransactionActive());
        applicationEventPublisher.publishEvent(new ReservationTokenEvent(userId));
        applicationEventPublisher.publishEvent(new PushEvent(payment.getId()));

        log.info("{}번 결제 완료", payment.getId());

        return payment.getId();
    }
}
