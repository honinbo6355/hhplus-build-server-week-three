//package com.example.hhplus.reservation.scheduler;
//
//import com.example.hhplus.reservation.domain.reservation.Reservation;
//import com.example.hhplus.reservation.domain.reservation.ReservationRepository;
//import com.example.hhplus.reservation.domain.reservation.ReservationStatus;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ReservationCheckScheduler {
//
//    private final ReservationRepository reservationRepository;
//
//    @Scheduled(fixedRateString = "5000")
//    @Transactional
//    public void cancelReservation() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime targetDateTime = now.minusMinutes(Reservation.RESERVATION_DURATION_MINUTE);
////        LocalDateTime targetDateTime = now.minusSeconds(8);
//        int cancelCount = reservationRepository.cancelReservation(ReservationStatus.CANCELLED, ReservationStatus.IN_PROGRESS, targetDateTime, now);
//        log.info("cancelCount : {}", cancelCount);
//    }
//}
