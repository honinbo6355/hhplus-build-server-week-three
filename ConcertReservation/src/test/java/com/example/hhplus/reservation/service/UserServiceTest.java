package com.example.hhplus.reservation.service;

import com.example.hhplus.reservation.domain.user.*;
import com.example.hhplus.reservation.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @Mock
    private ReservationTokenRepository reservationTokenRepository;

    @Mock
    private ReservationQueueRepository reservationQueueRepository;

    @Test
    @DisplayName("충전_정상일경우_성공")
    public void 충전_정상일경우_성공() throws Exception {
        // given
        long userId = 1L;
        long point = 3000L;
        long chargePoint = 5000L;
        User user = new User(userId, "유저1", point);
        PointHistory pointHistory = new PointHistory(1L, userId, TransactionType.CHARGE, chargePoint);

        // when
        when(userRepository.findByIdWithOptimisticLock(userId)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(pointHistory);

        // then
        long pointHistoryId = userService.chargePoint(userId, chargePoint);
        Assertions.assertThat(pointHistoryId).isEqualTo(pointHistory.getId());
        Assertions.assertThat(user.getAmount()).isEqualTo(point+chargePoint);
    }

    @Test
    @DisplayName("_0_이하_포인트_충전할경우_실패")
    public void _0_이하_포인트_충전할경우_실패() throws Exception {
        // given
        long userId = 1L;
        long point = 3000L;
        long chargePoint = -100L;
        User user = new User(userId, "유저1", point);

        // when
        when(userRepository.findByIdWithOptimisticLock(userId)).thenReturn(user);

        // then
        Assertions.assertThatThrownBy(() -> {
            userService.chargePoint(userId, chargePoint);
        }).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("잔액_조회_성공")
    public void 잔액_조회_성공() throws Exception {
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

    @Test
    @DisplayName("존재하지않는_유저일경우_토큰_발급_실패")
    public void 존재하지않는_유저일경우_토큰_발급_실패() {
        // given
        long userId = 1L;

        // when
        when(userRepository.findById(userId)).thenThrow(new NullPointerException());

        // then
        Assertions.assertThatThrownBy(() -> {
            userService.createToken(userId);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("토큰_존재하는_유저일경우_발급_성공")
    public void 토큰_존재하는_유저일경우_발급_성공() {
        // given
        long userId = 1L;
        ReservationToken reservationToken = new ReservationToken(UUID.randomUUID().toString(), ReservationTokenStatus.IN_PROGRESS, userId, LocalDateTime.now());

        // when
        when(userRepository.findById(userId)).thenReturn(new User(userId, "유저1", 5000L));
        when(reservationTokenRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(reservationToken));

        // then
        String token = userService.createToken(userId);

        Assertions.assertThat(token).isEqualTo(reservationToken.getTokenValue());
    }

    @Test
    @DisplayName("토큰_존재하는_유저일경우_발급_성공")
    public void 처음_토큰_발급하는경우_대기열_추가_성공() {
        // given
        long userId = 1L;

        // when
        when(userRepository.findById(userId)).thenReturn(new User(userId, "유저1", 5000L));
        when(reservationTokenRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(null));
        when(reservationQueueRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(null));

        // then
        String token = userService.createToken(userId);
        Assertions.assertThat(token).isEqualTo(null);
    }

    @Test
    @DisplayName("토큰_재발급하는경우_대기열_추가_성공")
    public void 토큰_재발급하는경우_대기열_추가_성공() {
        // given
        long userId = 1L;
        ReservationQueue reservationQueue = new ReservationQueue(ReservationQueueStatus.DONE, userId);

        // when
        when(userRepository.findById(userId)).thenReturn(new User(userId, "유저1", 5000L));
        when(reservationTokenRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(null));
        when(reservationQueueRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(reservationQueue));

        // then
        String token = userService.createToken(userId);
        Assertions.assertThat(token).isEqualTo(null);
        Assertions.assertThat(reservationQueue.getStatus()).isEqualTo(ReservationQueueStatus.WAITING);
    }
}
