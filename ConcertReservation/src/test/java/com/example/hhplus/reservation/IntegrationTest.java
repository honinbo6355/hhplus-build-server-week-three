package com.example.hhplus.reservation;

import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import com.example.hhplus.reservation.domain.concert.ConcertDetailRepository;
import com.example.hhplus.reservation.domain.payment.PaymentService;
import com.example.hhplus.reservation.domain.payment.PaymentStatus;
import com.example.hhplus.reservation.domain.reservation.*;
import com.example.hhplus.reservation.domain.user.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationQueueRepository reservationQueueRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ConcertDetailRepository concertDetailRepository;

    @Autowired
    private ReservationTokenRepository reservationTokenRepository;

    @Transactional
    @Test
    @DisplayName("한_유저가_토큰_발급_여러번_요청시_동시성_테스트")
    public void 한_유저가_토큰_발급_여러번_요청시_동시성_테스트() throws InterruptedException {
        // given
        int nThreads = 100;
        long userId = 16L;
        CountDownLatch latch = new CountDownLatch(nThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        // when
        for (int i=0; i<nThreads; i++) {
            executorService.submit(() -> {
                try {
                    userService.createToken(userId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        executorService.shutdown();
        latch.await();

        // then
        ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
        Assertions.assertThat(reservationQueue.getStatus()).isEqualTo(ReservationQueueStatus.WAITING);
    }

//    @Transactional
//    @Test
//    @DisplayName("동일한_콘서트_좌석을_여러_유저가_예약_동시성_테스트")
//    public void 동일한_콘서트_좌석을_여러_유저가_예약_동시성_테스트() throws InterruptedException {
//        // given
//        int userCount = 100;
//        long concertDetailId = 2L;
//        long seatId = 3L;
//        CountDownLatch latch = new CountDownLatch(userCount);
//        ExecutorService executorService = Executors.newFixedThreadPool(userCount);
//
//        // when
//        for (int i=1; i<=userCount; i++) {
//            Long userId = Long.valueOf(i);
//
//            executorService.submit(() -> {
//                try {
//                    reservationService.createReservation(concertDetailId, userId, seatId);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//        executorService.shutdown();
//        latch.await();
//
//        // then
//        Reservation reservation = reservationRepository.findByConcertDetailIdAndSeatId(concertDetailId, seatId).orElseThrow(NullPointerException::new);
//        Assertions.assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.IN_PROGRESS);
//    }
//
//    @Transactional
//    @Test
//    @DisplayName("콘서트_좌석_정원제한_동시성_테스트")
//    public void 콘서트_좌석_정원제한_동시성_테스트() throws InterruptedException {
//        // given
//        int userCount = 100;
//        long concertDetailId = 4L;
//        int seatCount = 50;
//        CountDownLatch latch = new CountDownLatch(userCount * seatCount);
//        ExecutorService executorService = Executors.newFixedThreadPool(userCount);
//
//        // when
//        for (int i=1; i<=userCount; i++) {
//            Long userId = Long.valueOf(i);
//
//            for (int j=1; j<=seatCount; j++) {
//                Long seatId = Long.valueOf(j);
//
//                executorService.submit(() -> {
//                    try {
//                        userService.createToken(userId);
//                        ReservationQueue reservationQueue = reservationQueueRepository.findByUserId(userId)
//                                .orElseThrow(NullPointerException::new);
//                        ReservationToken reservationToken = reservationTokenRepository.findByUserId(reservationQueue.getUserId())
//                                .orElse(null);
//                        if (reservationToken == null) {
//                            reservationToken = new ReservationToken(UUID.randomUUID().toString(), ReservationTokenStatus.IN_PROGRESS, reservationQueue.getUserId(), LocalDateTime.now());
//                        } else {
//                            reservationToken.inProgress();
//                        }
//                        reservationQueue.done();
//
//                        reservationTokenRepository.save(reservationToken);
//                        reservationQueueRepository.save(reservationQueue);
//
//                        long reservationId = reservationService.createReservation(concertDetailId, userId, seatId);
//                        Reservation reservation = reservationRepository.findById(reservationId);
//                        Seat seat = seatRepository.findById(reservation.getSeatId());
//                        paymentService.createPayment(reservationId, userId, seat.getPrice());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        latch.countDown();
//                    }
//                });
//            }
//        }
//        executorService.shutdown();
//        latch.await();
//
//        // then
//        ConcertDetail concertDetail = concertDetailRepository.findById(concertDetailId);
//        Assertions.assertThat(concertDetail.getReservedSeatNum()).isEqualTo(concertDetail.getMaxSeatNum());
//    }
}
