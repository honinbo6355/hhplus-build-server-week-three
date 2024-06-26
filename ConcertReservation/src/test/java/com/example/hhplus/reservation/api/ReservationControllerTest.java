package com.example.hhplus.reservation.api;

import com.example.hhplus.reservation.api.reservation.ReservationController;
import com.example.hhplus.reservation.domain.reservation.ReservationService;
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

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationTokenRepository reservationTokenRepository;

    @Test
    @DisplayName("좌석_예약_요청_성공")
    public void 좌석_예약_요청_성공() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;
        long reservationId = 10L;
        String requestJson = "{\"concertDetailId\":" + concertDetailId + ", \"userId\":" + userId + ", \"seatId\":" + seatId + "}";

        // when
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(reservationService.createReservation(concertDetailId, userId, seatId)).thenReturn(reservationId);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(reservationId));
    }

    @Test
    @DisplayName("이미_예약중인경우_실패")
    public void 이미_예약중인경우_실패() throws Exception {
        // given
        String token = "97ef7717-2bcd-4e23-9e7a-3d9bfa4ea5dd";
        long concertDetailId = 1L;
        long seatId = 5L;
        long userId = 1L;
        String requestJson = "{\"concertDetailId\":" + concertDetailId + ", \"userId\":" + userId + ", \"seatId\":" + seatId + "}";

        // when
        when(reservationTokenRepository.findByToken(token)).thenReturn(Optional.ofNullable(new ReservationToken(token, userId, LocalDateTime.now())));
        when(reservationService.createReservation(concertDetailId, userId, seatId)).thenThrow(new CustomException(ErrorCode.ALREADY_RESERVED));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .header("token", token)
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
