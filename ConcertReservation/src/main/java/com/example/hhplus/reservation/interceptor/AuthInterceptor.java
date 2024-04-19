package com.example.hhplus.reservation.interceptor;

import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.example.hhplus.reservation.domain.user.ReservationTokenRepository;
import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final ReservationTokenRepository reservationTokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        String token = request.getHeader("token");
        ReservationToken reservationToken = reservationTokenRepository.findByTokenValueAndStatus(token, ReservationTokenStatus.IN_PROGRESS)
                .orElse(null);
        if (reservationToken == null) {
            throw new CustomException(ErrorCode.NOT_AUTHORITY);
        }

        return true;
    }
}
