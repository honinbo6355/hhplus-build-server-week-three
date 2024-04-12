package com.example.hhplus.reservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_POINT(HttpStatus.BAD_REQUEST, "100", "잘못된 포인트 금액입니다."),
    ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "101", "이미 예약했거나 예약중입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
