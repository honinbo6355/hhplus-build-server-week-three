package com.example.hhplus.reservation.domain.user;

import com.example.hhplus.reservation.exception.CustomException;
import com.example.hhplus.reservation.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private long amount;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User(Long id, String name, long amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public void charge(long point) {
        if (point <= 0) {
            throw new CustomException(ErrorCode.INVALID_POINT);
        }
        this.amount += point;
    }

    public void use(long point) {
        if (amount < point) {
            throw new CustomException(ErrorCode.SHORTAGE_POINT);
        }
        this.amount -= point;
    }
}
