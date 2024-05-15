package com.example.hhplus.reservation.domain.user;

import com.example.hhplus.reservation.api.user.dto.*;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReservationQueueRepository reservationQueueRepository;
    private final ReservationTokenRepository reservationTokenRepository;

    @Transactional
    public Long chargePoint(long userId, long point) {
        User user = userRepository.findByIdWithOptimisticLock(userId);
        user.charge(point);
        userRepository.save(user);
        PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(null, userId, TransactionType.CHARGE, point));
        return pointHistory.getId();
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

        try {
            ReservationToken reservationToken = reservationTokenRepository.findByUserId(userId) // 유저 토큰 조회
                    .orElse(null);

            if (reservationToken != null) {
                return reservationToken.getTokenValue();
            }

            // 유저 대기열 조회
            ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId)
                    .orElse(null);

            if (reservationQueue == null) { // 유저 대기열이 존재하지않는다면
                reservationQueueRepository.save(new ReservationQueue(userId));
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }

        return null;
    }

    @Transactional(readOnly = true)
    public TokenDetailResponse getTokenDetail(Long userId) {
        try {
            ReservationToken reservationToken = reservationTokenRepository.findByUserId(userId)
                    .orElse(null);

            if (reservationToken != null) {
                return new TokenDetailResponse(null, reservationToken.getTokenValue());
            }
            ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId)
                    .orElse(null);
            if (reservationQueue != null) {
                long waitingCount = reservationQueueRepository.countByWaitingList(reservationQueue);
                return new TokenDetailResponse(waitingCount+1, null);
            }

            return new TokenDetailResponse(null, null);

        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }
    }
}
