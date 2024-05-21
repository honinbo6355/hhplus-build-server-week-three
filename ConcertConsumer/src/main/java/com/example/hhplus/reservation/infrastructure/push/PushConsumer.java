package com.example.hhplus.reservation.infrastructure.push;

import com.example.hhplus.reservation.external.PushClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushConsumer {

    private final PushClient pushClient;

    @KafkaListener(topics = "push-topic", groupId = "consumer_group01")
    public void consume(String message) throws IOException {
        System.out.printf("Push Consumed Message : %s%n", message);

        Long paymentId = Long.parseLong(message);
        pushClient.sendPush(paymentId);
    }
}
