package com.example.hhplus.reservation.infrastructure.payment;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.payment.Payment;
import com.example.hhplus.reservation.domain.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "test", name = "payment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;
    private long amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment toDomain() {
        return new Payment(id, reservationId, amount, status, getCreatedAt(), getUpdatedAt());
    }

    public static PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(payment.getId(), payment.getReservationId(), payment.getAmount(), payment.getStatus());
    }
}
