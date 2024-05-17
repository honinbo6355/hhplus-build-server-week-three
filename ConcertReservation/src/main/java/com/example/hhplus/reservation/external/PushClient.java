package com.example.hhplus.reservation.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushClient {
    private final WebClient webClient;

    public void sendPush() {
        String result = webClient.post()
                .uri("api/external/push")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("push 발송 결과 : {}", result);
    }
}
