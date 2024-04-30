package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.User;
import com.example.hhplus.reservation.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(long userId) {
        return userJpaRepository.findById(userId)
                .map(UserEntity::toDomain)
                .orElse(null);
    }

    @Override
    public User findByIdWithLock(long userId) {
        return userJpaRepository.findByIdWithOptimisticLock(userId)
                .toDomain();
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.toEntity(user)).toDomain();
    }
}
