package com.example.hhplus.reservation.api.payment;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.payment.dto.PaymentRequest;
import com.example.hhplus.reservation.api.payment.dto.PaymentResponse;
import com.example.hhplus.reservation.domain.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<BasicResponse<PaymentResponse>> createPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest.reservationId(), paymentRequest.userId(), paymentRequest.point());
        return new ResponseEntity<>(new BasicResponse<>(paymentResponse, null), HttpStatus.OK);
    }
}
