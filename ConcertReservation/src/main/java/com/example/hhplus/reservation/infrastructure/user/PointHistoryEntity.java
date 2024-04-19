package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.user.PointHistory;
import com.example.hhplus.reservation.domain.user.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "test", name = "point_history",
        uniqueConstraints = {
                @UniqueConstraint(name = "point_history_unique_key",
                        columnNames = {"userId", "transactionType"})
        })
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private long amount;

    public static PointHistoryEntity toEntity(PointHistory pointHistory) {
        PointHistoryEntity pointHistoryEntity = new PointHistoryEntity(pointHistory.getId(), pointHistory.getUserId(), pointHistory.getTransactionType(), pointHistory.getAmount());
        return pointHistoryEntity;
    }

    public PointHistory toDomain() {
        return new PointHistory(id, userId, transactionType, amount, getCreatedAt(), getUpdatedAt());
    }
}
