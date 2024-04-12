package com.example.hhplus.reservation.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Concert {
    private Long id;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Concert(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
