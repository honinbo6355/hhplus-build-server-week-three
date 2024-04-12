package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.payment.PaymentController;
import com.example.hhplus.reservation.api.payment.dto.PaymentResponse;
import com.example.hhplus.reservation.api.user.dto.PointChargeResponse;
import com.example.hhplus.reservation.domain.payment.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("결제_성공할경우")
    public void 결제_성공할경우() throws Exception {
        // given
        long reservationId = 10L;
        long userId = 1L;
        long point = 15000L;

        // when
        String requestJson = "{\"reservationId\":" + reservationId + ", \"userId\":" + userId + ", \"point\":" + point + "}";
        when(paymentService.createPayment(reservationId, userId, point)).thenReturn(PaymentResponse.SUCCESS);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PaymentResponse.SUCCESS.toString()));
    }
}
