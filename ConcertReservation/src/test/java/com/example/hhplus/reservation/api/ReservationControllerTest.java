package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.reservation.ReservationController;
import com.example.hhplus.reservation.api.reservation.dto.ReservationRequest;
import com.example.hhplus.reservation.api.reservation.dto.ReservationResponse;
import com.example.hhplus.reservation.api.user.dto.PointChargeResponse;
import com.example.hhplus.reservation.domain.reservation.ReservationService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("좌석_예약_요청_성공할경우")
    public void 좌석_예약_요청_성공할경우() throws Exception {
        // given
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;
        String requestJson = "{\"concertDetailId\":" + concertDetailId + ", \"userId\":" + userId + ", \"seatId\":" + seatId + "}";

        // when
        when(reservationService.createReservation(concertDetailId, userId, seatId)).thenReturn(ReservationResponse.SUCCESS);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PointChargeResponse.SUCCESS.toString()));
    }

    @Test
    @DisplayName("이미_예약중인경우")
    public void 이미_예약중인경우() throws Exception {
        // given
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;
        String requestJson = "{\"concertDetailId\":" + concertDetailId + ", \"userId\":" + userId + ", \"seatId\":" + seatId + "}";

        // when
        when(reservationService.createReservation(concertDetailId, userId, seatId)).thenThrow(new CustomException(ErrorCode.ALREADY_RESERVED));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ErrorCode.ALREADY_RESERVED.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value(ErrorCode.ALREADY_RESERVED.getMessage()));
    }
}
