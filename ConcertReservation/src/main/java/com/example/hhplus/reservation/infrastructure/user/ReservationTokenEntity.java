package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTokenEntity {
    private String tokenValue;
    private Long userId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss:SSS")
    private LocalDateTime issuedAt;

    public static final int MAX_TOKEN_SIZE = 10;
    public static final long TOKEN_DURATION_MINUTE = 5;

    public ReservationToken toDomain() {
        return new ReservationToken(tokenValue, userId, issuedAt);
    }

    public static ReservationTokenEntity toEntity(ReservationToken reservationToken) {
        return new ReservationTokenEntity(reservationToken.getTokenValue(), reservationToken.getUserId(), reservationToken.getIssuedAt());
    }

    @JsonIgnore
    public boolean isExpired() {
        return issuedAt.isBefore(LocalDateTime.now().minusMinutes(TOKEN_DURATION_MINUTE));
//        return issuedAt.isBefore(LocalDateTime.now().minusSeconds(5));
    }
}
