package com.example.hhplus.reservation.service;

import com.example.hhplus.reservation.api.user.dto.PointChargeResponse;
import com.example.hhplus.reservation.api.user.dto.PointResponse;
import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @Test
    @DisplayName("충전_성공할경우")
    public void 충전_성공할경우() throws Exception {
        // given
        long userId = 1L;
        long point = 3000L;
        long chargePoint = 5000L;
        User user = new User(userId, "유저1", point);
        PointHistory pointHistory = new PointHistory(userId, TransactionType.CHARGE, chargePoint);

        // when
        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(pointHistory);

        // then
        PointChargeResponse pointChargeResponse = userService.chargePoint(userId, chargePoint);
        Assertions.assertThat(pointChargeResponse).isEqualTo(PointChargeResponse.SUCCESS);
        Assertions.assertThat(user.getAmount()).isEqualTo(point+chargePoint);
    }

    @Test
    @DisplayName("_0_이하_포인트_충전할경우")
    public void _0_이하_포인트_충전할경우() throws Exception {
        // given
        long userId = 1L;
        long point = 3000L;
        long chargePoint = -100L;
        User user = new User(userId, "유저1", point);

        // when
        when(userRepository.findById(userId)).thenReturn(user);

        // then
        Assertions.assertThatThrownBy(() -> {
            userService.chargePoint(userId, chargePoint);
        }).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("잔액_조회_성공할경우")
    public void 잔액_조회_성공할경우() throws Exception {
        // given
        long userId = 1L;
        User user = new User(userId, "유저1", 5000L);

        // when
        when(userRepository.findById(userId)).thenReturn(user);

        // then
        User resultUser = userService.getPoint(userId);

        Assertions.assertThat(resultUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(resultUser.getAmount()).isEqualTo(user.getAmount());
    }
}
