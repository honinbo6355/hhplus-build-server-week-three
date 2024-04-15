package com.example.hhplus.reservation.domain.payment;

import com.example.hhplus.reservation.api.payment.dto.PaymentResponse;
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

    // TODO 토큰 만료 처리
    @Transactional
    public PaymentResponse createPayment(long reservationId, long userId, long point) {
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
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);
        pointHistoryRepository.save(new PointHistory(userId, TransactionType.USE, point));
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NullPointerException();
        }
        user.use(point);
        userRepository.save(user);
        paymentRepository.save(new Payment(reservationId, point, PaymentStatus.SUCCESSED));

        return PaymentResponse.SUCCESS;
    }
}
