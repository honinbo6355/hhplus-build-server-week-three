package com.example.hhplus.reservation.domain.user;

public interface UserRepository {
    User findById(long userId);
    User findByIdWithLock(long userId);
    User save(User user);
}
