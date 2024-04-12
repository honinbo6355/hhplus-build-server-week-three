package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.user.UserController;
import com.example.hhplus.reservation.api.user.dto.PointChargeRequest;
import com.example.hhplus.reservation.api.user.dto.PointChargeResponse;
import com.example.hhplus.reservation.api.user.dto.PointResponse;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("충전_성공할경우")
    public void 충전_성공할경우() throws Exception {
        // given
        long userId = 1L;
        long point = 5000L;
        String requestJson = "{\"userId\":" + userId + ", \"point\":" + point + "}";

        // when
        when(userService.chargePoint(userId, point)).thenReturn(PointChargeResponse.SUCCESS);

        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PointChargeResponse.SUCCESS.toString()));
    }

    @Test
    @DisplayName("_0_이하_포인트_충전할경우")
    public void _0_이하_포인트_충전할경우() throws Exception {
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
    @DisplayName("잔액_조회_성공할경우")
    public void 잔액_조회_성공할경우() throws Exception {
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
}
