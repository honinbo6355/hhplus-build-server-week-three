package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.user.UserController;
import com.example.hhplus.reservation.domain.user.ReservationTokenRepository;
import com.example.hhplus.reservation.domain.user.User;
import com.example.hhplus.reservation.domain.user.UserService;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ReservationTokenRepository reservationTokenRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("충전_정상일경우_성공")
    public void 충전_정상일경우_성공() throws Exception {
        // given
        long userId = 1L;
        long point = 5000L;
        long pointHistoryId = 5L;
        String requestJson = "{\"userId\":" + userId + ", \"point\":" + point + "}";

        // when
        when(userService.chargePoint(userId, point)).thenReturn(pointHistoryId);

        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(pointHistoryId));
    }

    @Test
    @DisplayName("_0_이하_포인트_충전할경우_실패")
    public void _0_이하_포인트_충전할경우_실패() throws Exception {
        // given
        long userId = 1L;
        long point = -100L;
        String requestJson = "{\"userId\":" + userId + ", \"point\":" + point + "}";

        // when
        when(userService.chargePoint(userId, point)).thenThrow(new CustomException(ErrorCode.INVALID_POINT));

        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ErrorCode.INVALID_POINT.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value(ErrorCode.INVALID_POINT.getMessage()));
    }

    @Test
    @DisplayName("잔액_조회_성공")
    public void 잔액_조회_성공() throws Exception {
        // given
        long userId = 1L;
        long point = 5000L;

        // when
        when(userService.getPoint(userId)).thenReturn(new User(userId, "유저1", point));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + userId + "/point")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.point").value(point));
    }

    @Test
    @DisplayName("토큰_발급_성공")
    public void 토큰_발급_성공() throws Exception {
        // given
        long userId = 1L;
        String token = UUID.randomUUID().toString();
        String requestJson = "{\"userId\":" + userId + "}";

        // when
        when(userService.createToken(userId)).thenReturn(token);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/token")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.token").value(token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("대기열_추가_성공")
    public void 대기열_추가_성공() throws Exception {
        // given
        long userId = 1L;
        String requestJson = "{\"userId\":" + userId + "}";

        // when
        when(userService.createToken(userId)).thenReturn(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/token")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.token").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("존재하지않는_유저일경우_실패")
    public void 존재하지않는_유저일경우_실패() throws Exception {
        // given
        long userId = 1L;
        String requestJson = "{\"userId\":" + userId + "}";

        // when
        when(userService.createToken(userId)).thenThrow(new NullPointerException());

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/token")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty());
    }
}
