package com.example.hhplus.reservation.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserService userService;

    public Long chargePoint(long userId, long point) {
        int retryCount = 10;

        while (retryCount-- > 0) {
            try {
                Long pointHistoryId = userService.chargePoint(userId, point);

                return pointHistoryId;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.info("chargePoint 재시도 카운트 : {}", retryCount);
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); // 1000~2000ms 대기 후 재시도.
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        throw new RuntimeException();
    }
}
