package com.example.hhplus.reservation.api.payment;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.payment.dto.PaymentRequest;
import com.example.hhplus.reservation.api.payment.dto.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping
    public ResponseEntity<BasicResponse<PaymentResponse>> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return null;
    }
}
