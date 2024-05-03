package com.example.hhplus.reservation.domain.user;

public interface UserRepository {
    User findById(long userId);
    User findByIdWithOptimisticLock(long userId);
    User findByIdWithPessimisticLock(long userId);
    User save(User user);
}
