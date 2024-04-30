package com.example.hhplus.reservation.infrastructure.user;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    @Lock(value = LockModeType.OPTIMISTIC)
    @Query(value = "select a from UserEntity a where a.id = :id")
    UserEntity findByIdWithOptimisticLock(long id);
}
