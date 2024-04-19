package com.example.hhplus.reservation.scheduler;

import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.infrastructure.user.ReservationQueueEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCheckScheduler {

    private final ReservationTokenRepository reservationTokenRepository;
    private final ReservationQueueRepository reservationQueueRepository;

//    @Scheduled(fixedRateString = "5000")
    @Scheduled(fixedRateString = "10000")
    @Transactional
    public void expireToken() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetDateTime = now.minusMinutes(ReservationToken.TOKEN_DURATION_MINUTE);
//        LocalDateTime targetDateTime = now.minusSeconds(8);
        int expireCount = reservationTokenRepository.expireDurationOverToken(ReservationTokenStatus.FINISHED, ReservationTokenStatus.IN_PROGRESS, targetDateTime, now);
        log.info("expireCount : {}", expireCount);
    }

    @Scheduled(fixedRateString = "5000")
    @Transactional
    public void queueToIssueToken() {
        int activeTokenCount = reservationTokenRepository.countByStatus(ReservationTokenStatus.IN_PROGRESS);
        if (activeTokenCount < ReservationToken.MAX_IN_PROGRESS_SIZE) {
            List<ReservationQueue> reservationQueueList = reservationQueueRepository.findByWaitingList(ReservationQueueStatus.WAITING, PageRequest.of(0, ReservationToken.MAX_IN_PROGRESS_SIZE-activeTokenCount));
            reservationQueueList.stream().forEach(reservationQueue -> {
                ReservationToken reservationToken = reservationTokenRepository.findByUserId(reservationQueue.getUserId())
                        .orElse(null);
                if (reservationToken == null) {
                    reservationToken = new ReservationToken(UUID.randomUUID().toString(), ReservationTokenStatus.IN_PROGRESS, reservationQueue.getUserId(), LocalDateTime.now());
                } else {
                    reservationToken.inProgress();
                }
                reservationQueue.done();

                reservationTokenRepository.save(reservationToken);
                reservationQueueRepository.save(reservationQueue);
            });

            if (reservationQueueList.size() > 0) {
                log.info("토큰 발급 {}개 완료", reservationQueueList.size());
            }
        }
    }
}
