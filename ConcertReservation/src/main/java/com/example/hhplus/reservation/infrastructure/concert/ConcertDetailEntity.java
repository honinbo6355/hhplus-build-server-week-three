package com.example.hhplus.reservation.infrastructure.concert;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.concert.ConcertDetail;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GeneratorType;

import java.time.LocalDateTime;

@Entity
@Table(schema = "test", name = "concert_detail")
@Getter
public class ConcertDetailEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId;
    private LocalDateTime startsAt;
    private int maxSeatNum;
    private int reservedSeatNum;

    public ConcertDetail toDomain() {
        return new ConcertDetail(id, concertId, startsAt, maxSeatNum, reservedSeatNum, getCreatedAt(), getUpdatedAt());
    }
}
