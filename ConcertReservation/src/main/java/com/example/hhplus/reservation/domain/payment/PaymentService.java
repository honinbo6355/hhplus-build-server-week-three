package com.example.hhplus.reservation.domain.payment;

import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import com.example.hhplus.reservation.domain.concert.ConcertDetailRepository;
import com.example.hhplus.reservation.domain.reservation.*;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NullPointerException();
        }

        ConcertDetail concertDetail = concertDetailRepository.findById(reservation.getConcertDetailId());
        if (concertDetail == null) {
            throw new NullPointerException();
        }

        ReservationToken reservationToken = reservationTokenRepository.findByUserId(userId)
                .orElse(null);
        if (reservationToken == null) {
            throw new NullPointerException();
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        user.use(point);
        concertDetail.increaseReservedSeatNum();
        reservationToken.expire();

        Payment payment = paymentRepository.save(new Payment(reservationId, point, PaymentStatus.SUCCESSED));

        reservationRepository.save(reservation);
        pointHistoryRepository.save(new PointHistory(null, userId, TransactionType.USE, point));
        userRepository.save(user);
        concertDetailRepository.save(concertDetail);
        reservationTokenRepository.save(reservationToken);

        return payment.getId();
    }
}
