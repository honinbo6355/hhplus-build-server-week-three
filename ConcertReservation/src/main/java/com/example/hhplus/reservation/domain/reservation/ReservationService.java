package com.example.hhplus.reservation.domain.reservation;

import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationInternalService reservationInternalService;
    private final RedissonClient redissonClient;

    /**
     * 1.
     * @Transactional 여부 : 테스트 코드 o, outer method x, internal method o
     * 1000번 발생시
     *
     * 첫번째 : 성공
     * 두번째 : 실패
     * 세번째 : 실패
     * Lock 획득 실패만 발생
     * 테스트 코드에 @Transactional 빼도 동일한 결과
     *
     * 2.
     * @Transactional 여부 : 테스트 코드 o, outer method o, internal method x
     * 1000번 발생시
     *
     * 첫번째 : 성공
     * 두번째 : 성공
     * 세번째 : 성공
     * 이미 예약했거나 예약중입니다만 발생
     * 테스트 코드에 @Transactional 빼도 동일한 결과
     */
    public Long createReservation(long concertDetailId, long userId, long seatId) {
        RLock rLock = redissonClient.getLock(String.format("reservation-%d-%d", concertDetailId, seatId));

        try {
//            boolean isLock = rLock.tryLock(0L, 3L, TimeUnit.SECONDS);
            boolean isLock = rLock.tryLock(5L, 3L, TimeUnit.SECONDS);
//            boolean isLock = rLock.tryLock(10L, 3L, TimeUnit.SECONDS);
            if (!isLock) {
                throw new CustomException(ErrorCode.LOCK_NOT_AVAILABLE);
            }
            return reservationInternalService.reserve(concertDetailId, userId, seatId);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                // TODO 트랜잭션을 먼저 선언한 방식으로 테스트해도 성공하는 문제 : 쓰레드 슬립을 활용해서 unlock, commit 시점 확인
                log.info("unlock complete: {}", rLock.getName());
            }
        }
    }
}
