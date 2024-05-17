package com.example.hhplus.reservation.external;

import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import com.example.hhplus.reservation.infrastructure.push.PushEntity;
import com.example.hhplus.reservation.infrastructure.push.PushJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushClient {
    private final WebClient webClient;
    private final PushJpaRepository pushJpaRepository;

    @Async("threadPoolTaskExecutor")
    public void sendPush() {
        PushEntity pushEntity = pushJpaRepository.save(new PushEntity(null, PushEntity.PushStatus.SENDING));

        try {
            String result = webClient.post()
                    .uri("api/external/push")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("push 발송 결과 : {}", result);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }

        pushEntity.setStatus(PushEntity.PushStatus.COMPLETED);
        pushJpaRepository.save(pushEntity);
    }
}
