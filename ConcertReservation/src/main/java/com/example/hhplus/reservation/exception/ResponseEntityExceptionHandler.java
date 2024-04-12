package com.example.hhplus.reservation.exception;

import com.example.hhplus.reservation.api.common.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse<Void>> handleException(Exception e) {
        return new ResponseEntity<>(new BasicResponse<>(null, new BasicResponse.BasicError("500", "서버 에러가 발생했습니다.")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BasicResponse<Void>> handleCustomException(CustomException e) {
        return new ResponseEntity<>(new BasicResponse<>(null, new BasicResponse.BasicError(e.getCode(), e.getMessage())), e.getStatus());
    }
}
