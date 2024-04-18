package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationQueueJpaRepository extends JpaRepository<ReservationQueueEntity, Long> {
    Optional<ReservationQueueEntity> findByUserId(long userId);

    @Query(value = "select count(*) from test.reservation_queue "
            + "where status = 'WAITING' "
            + "and updated_at < (select updated_at from test.reservation_queue where user_id = :userId)",
            nativeQuery = true)
    int countByWaitingList(long userId);
}
