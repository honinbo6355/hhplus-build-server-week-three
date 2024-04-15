package com.example.hhplus.reservation.infrastructure.payment;

import com.example.hhplus.reservation.domain.payment.Payment;
import com.example.hhplus.reservation.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.toEntity(payment)).toDomain();
    }
}
