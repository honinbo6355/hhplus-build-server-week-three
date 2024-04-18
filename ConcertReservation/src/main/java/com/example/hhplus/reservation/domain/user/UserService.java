package com.example.hhplus.reservation.domain.user;

import com.example.hhplus.reservation.api.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReservationQueueRepository reservationQueueRepository;
    private final ReservationTokenRepository reservationTokenRepository;

    // TODO 동시성 제어
    @Transactional
    public PointChargeResponse chargePoint(long userId, long point) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NullPointerException();
        }
        user.charge(point);
        userRepository.save(user);
        pointHistoryRepository.save(new PointHistory(userId, TransactionType.CHARGE, point));
        return PointChargeResponse.SUCCESS;
    }

    @Transactional(readOnly = true)
    public User getPoint(long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NullPointerException();
        }
        return user;
    }

    @Transactional
    public String createToken(long userId) {
        User user = userRepository.findById(userId);

        // 존재하지 않는 유저일 경우 NPE
        if (user == null) {
            throw new NullPointerException();
        }

        // 유저 대기열 조회
        ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId)
                .orElse(null);
        ReservationToken reservationToken = reservationTokenRepository.findByUserId(userId) // 유저 토큰 조회
                .orElse(null);
        String token = null;

        if (reservationToken != null && reservationToken.getStatus() == ReservationTokenStatus.IN_PROGRESS) {
            token = reservationToken.getTokenValue();
            return token;
        }

        if (reservationQueue == null) { // 유저 대기열이 존재하지않는다면
            int inProgressCount = reservationTokenRepository.countByStatus(ReservationTokenStatus.IN_PROGRESS); // 토큰 발급 수량 조회
            if (ReservationToken.MAX_IN_PROGRESS_SIZE > inProgressCount) { // 토큰 수량이 여유가 있다면
                if (reservationToken == null) { // 유저 토큰이 존재하지않는다면
                    reservationToken = new ReservationToken(UUID.randomUUID().toString(), ReservationTokenStatus.IN_PROGRESS, userId, LocalDateTime.now());
                } else if (reservationToken.getStatus() == ReservationTokenStatus.FINISHED) { // 유저 토큰이 만료되었다면
                    reservationToken.inProgress();
                }
                reservationTokenRepository.save(reservationToken);
                token = reservationToken.getTokenValue();
            } else { // 토큰 수량이 여유가 없다면
                reservationQueueRepository.save(new ReservationQueue(ReservationQueueStatus.WAITING, userId));
            }
        } else if (reservationQueue.getStatus() == ReservationQueueStatus.DONE) { // 유저 대기열이 DONE 상태라면
            reservationQueue.setStatus(ReservationQueueStatus.WAITING);
            reservationQueueRepository.save(reservationQueue);
        }

        return token;
    }

    @Transactional(readOnly = true)
    public TokenDetailResponse getTokenDetail(Long userId) {
        ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId)
                .orElse(null);
        if (reservationQueue == null) {
            return new TokenDetailResponse(null, null, null);
        }
        if (reservationQueue.getStatus() == ReservationQueueStatus.WAITING) {
            int waitingCount = reservationQueueRepository.countByWaitingList(userId);
            return new TokenDetailResponse(waitingCount+1, null, TokenDetailStatus.valueOf(ReservationQueueStatus.WAITING.name()));
        }
        ReservationToken reservationToken = reservationTokenRepository.findByUserId(userId)
                .orElse(null);
        return new TokenDetailResponse(null, reservationToken.getTokenValue(), TokenDetailStatus.valueOf(reservationToken.getStatus().name()));
    }
}
