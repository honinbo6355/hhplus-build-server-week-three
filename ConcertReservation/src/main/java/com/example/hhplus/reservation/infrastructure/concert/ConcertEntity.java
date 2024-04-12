package com.example.hhplus.reservation.infrastructure.concert;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.concert.Concert;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(schema = "test", name = "concert")
@Getter
public class ConcertEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Concert toDomain() {
        return new Concert(id, name);
    }
}
