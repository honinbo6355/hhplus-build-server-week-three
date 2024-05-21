package com.example.hhplus.reservation.scheduler;

import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.infrastructure.user.ReservationQueueEntity;
import com.example.hhplus.reservation.infrastructure.user.ReservationTokenEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCheckScheduler {

    private final ReservationTokenRepository reservationTokenRepository;
    private final ReservationQueueRepository reservationQueueRepository;

//    @Scheduled(fixedRateString = "5000")
//    @Transactional
//    public void expireToken() {
//        try {
//            long expireCount = reservationTokenRepository.expireDurationOverToken();
//            if (expireCount > 0) {
//                log.info("expireCount : {}", expireCount);
//            }
//        } catch (JsonProcessingException e) {
//            log.error("expireToken 에러 : {}", e);
//        }
//    }

    @Scheduled(fixedRateString = "10000")
    @Transactional
    public void queueToIssueToken() {
        long tokenCount = reservationTokenRepository.countToken();
        if (tokenCount < ReservationTokenEntity.MAX_TOKEN_SIZE) {
            Set<ReservationQueue> reservationQueues = reservationQueueRepository.findRange(0, ReservationTokenEntity.MAX_TOKEN_SIZE-tokenCount-1);

            if (reservationQueues.isEmpty()) {
                return;
            }

            try {
                for (ReservationQueue reservationQueue : reservationQueues) {
                    ReservationToken reservationToken = reservationTokenRepository.findByUserId(reservationQueue.getUserId())
                                    .orElse(null);
                    if (reservationToken == null) {
                        reservationTokenRepository.save(new ReservationToken(UUID.randomUUID().toString(), reservationQueue.getUserId(), LocalDateTime.now()));
                    }
                }
                long removedCount = reservationQueueRepository.removeRange(0, ReservationTokenEntity.MAX_TOKEN_SIZE-tokenCount-1);
                log.info("removedCount : {}", removedCount);
            } catch (JsonProcessingException e) {
                log.error("queueToIssueToken 에러 : {}", e);
            }
        }
    }
}
