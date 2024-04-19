package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationQueueJpaRepository extends JpaRepository<ReservationQueueEntity, Long> {
    Optional<ReservationQueueEntity> findByUserId(long userId);

    @Query(value = "select count(*) from test.reservation_queue "
            + "where status = 'WAITING' "
            + "and updated_at < (select updated_at from test.reservation_queue where user_id = :userId)",
            nativeQuery = true)
    int countByWaitingList(long userId);

    @Query(value = "select r from ReservationQueueEntity r where r.status = :status order by r.updatedAt asc")
    List<ReservationQueueEntity> findByWaitingList(ReservationQueueStatus status, Pageable pageable);
}
