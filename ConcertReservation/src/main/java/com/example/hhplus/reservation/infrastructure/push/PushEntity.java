package com.example.hhplus.reservation.infrastructure.push;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "test", name = "push")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PushEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PushStatus status;

    public enum PushStatus {
        SENDING,
        COMPLETED
    }
}