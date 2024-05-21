package com.example.hhplus.reservation.infrastructure.push;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "push")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PushEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PushStatus status;

    private Long paymentId;

    public enum PushStatus {
        SENDING,
        COMPLETED
    }
}
