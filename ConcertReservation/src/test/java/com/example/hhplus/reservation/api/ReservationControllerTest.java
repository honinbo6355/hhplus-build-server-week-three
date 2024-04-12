//package com.example.hhplus.reservation.api;
//
//import com.example.hhplus.reservation.api.reservation.ReservationController;
//import com.example.hhplus.reservation.api.reservation.dto.ReservationRequest;
//import com.example.hhplus.reservation.domain.reservation.ReservationService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(ReservationController.class)
//public class ReservationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReservationService reservationService;
//
//    @Test
//    @DisplayName("좌석_예약_요청_성공할경우")
//    public void 좌석_예약_요청_성공할경우() throws Exception {
//        // given
//        long concertDetailId = 1L;
//        long seatId = 5L;
//        ReservationRequest reservationRequest = new ReservationRequest(concertDetailId, seatId);
//
//        // when
//        when(reservationService.createReservation()).thenReturn()
//        // then
//    }
//}
