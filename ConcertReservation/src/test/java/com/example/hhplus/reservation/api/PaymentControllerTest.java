package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.payment.PaymentController;
import com.example.hhplus.reservation.domain.payment.PaymentService;
import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.example.hhplus.reservation.domain.user.ReservationTokenRepository;
import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ReservationTokenRepository reservationTokenRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("결제_성공")
    public void 결제_성공() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;
        long paymentId = 7L;

        // when
        String requestJson = "{\"reservationId\":" + reservationId + ", \"userId\":" + userId + ", \"point\":" + point + "}";
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(paymentService.createPayment(reservationId, userId, point)).thenReturn(paymentId);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(paymentId));
    }

    @Test
    @DisplayName("예약한_사용자가_아닌경우_실패")
    public void 예약한_사용자가_아닌경우_실패() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;

        // when
        String requestJson = "{\"reservationId\":" + reservationId + ", \"userId\":" + userId + ", \"point\":" + point + "}";
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(paymentService.createPayment(reservationId, userId, point)).thenThrow(new CustomException(ErrorCode.INVALID_USER));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ErrorCode.INVALID_USER.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value(ErrorCode.INVALID_USER.getMessage()));
    }

    @Test
    @DisplayName("좌석_금액과_일치하지않는경우_실패")
    public void 좌석_금액과_일치하지않는경우_실패() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;

        // when
        String requestJson = "{\"reservationId\":" + reservationId + ", \"userId\":" + userId + ", \"point\":" + point + "}";
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(paymentService.createPayment(reservationId, userId, point)).thenThrow(new CustomException(ErrorCode.INVALID_PAYMENT_POINT));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ErrorCode.INVALID_PAYMENT_POINT.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value(ErrorCode.INVALID_PAYMENT_POINT.getMessage()));
    }

    @Test
    @DisplayName("잘못된_예약상태_인경우_실패")
    public void 잘못된_예약상태_인경우_실패() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;

        // when
        String requestJson = "{\"reservationId\":" + reservationId + ", \"userId\":" + userId + ", \"point\":" + point + "}";
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(paymentService.createPayment(reservationId, userId, point)).thenThrow(new CustomException(ErrorCode.INVALID_RESERVATION_STATUS));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ErrorCode.INVALID_RESERVATION_STATUS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value(ErrorCode.INVALID_RESERVATION_STATUS.getMessage()));
    }
}
