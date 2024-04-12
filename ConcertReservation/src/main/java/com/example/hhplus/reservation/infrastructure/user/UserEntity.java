package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.BaseTimeEntity;
import com.example.hhplus.reservation.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(schema = "test", name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private long amount;

    public User toDomain() {
        return new User(id, name, amount, getCreatedAt(), getUpdatedAt());
    }

    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getAmount());

        return userEntity;
    }
}
