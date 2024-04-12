package com.example.hhplus.reservation.domain.user;

import com.example.hhplus.reservation.api.user.dto.PointChargeResponse;
import com.example.hhplus.reservation.api.user.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    // TODO 동시성 제어
    @Transactional
    public PointChargeResponse chargePoint(long userId, long point) {
        User user = userRepository.findById(userId);
        user.charge(point);
        userRepository.save(user);
        pointHistoryRepository.save(new PointHistory(userId, TransactionType.CHARGE, point));
        return PointChargeResponse.SUCCESS;
    }

    @Transactional(readOnly = true)
    public User getPoint(long userId) {
        User user = userRepository.findById(userId);
        return user;
    }
}
