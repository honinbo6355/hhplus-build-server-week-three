package com.example.hhplus.reservation.domain.payment;

import com.example.hhplus.reservation.api.payment.dto.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Transactional
    public PaymentResponse createPayment(long reservationId, long userId, long point) {
        return null;
    }
}
