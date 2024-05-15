package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationQueue;
import com.example.hhplus.reservation.domain.user.ReservationQueueRepository;
import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReservationQueueRepositoryImpl implements ReservationQueueRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final String REDIS_QUEUE_KEY = "reservation-queue";

    @Override
    public ReservationQueue save(ReservationQueue reservationQueue) throws JsonProcessingException {
        redisTemplate.opsForZSet().add(REDIS_QUEUE_KEY, objectMapper.writeValueAsString(ReservationQueueEntity.toEntity(reservationQueue)), Instant.now().toEpochMilli());
        return reservationQueue;
    }

    @Override
    public ReservationQueue remove(ReservationQueue reservationQueue) throws JsonProcessingException {
        redisTemplate.opsForZSet().remove(REDIS_QUEUE_KEY, objectMapper.writeValueAsString(ReservationQueueEntity.toEntity(reservationQueue)));
        return reservationQueue;
    }

    @Override
    public Optional<ReservationQueue> findByUserId(long userId) throws JsonProcessingException {
        Set<String> members = redisTemplate.opsForZSet().range(REDIS_QUEUE_KEY, 0, -1);
        for (String member : members) {
            ReservationQueueEntity reservationQueueEntity = objectMapper.readValue(member, ReservationQueueEntity.class);

            if (reservationQueueEntity.getUserId().equals(userId)) {
                return Optional.of(reservationQueueEntity.toDomain());
            }
        }

        return Optional.empty();
    }

    @Override
    public long countByWaitingList(ReservationQueue reservationQueue) throws JsonProcessingException {
        return redisTemplate.opsForZSet().rank(REDIS_QUEUE_KEY, objectMapper.writeValueAsString(ReservationQueueEntity.toEntity(reservationQueue)));
    }

    @Override
    public Set<ReservationQueue> findRange(long startIdx, long endIdx) {
        return redisTemplate.opsForZSet().range(REDIS_QUEUE_KEY, startIdx, endIdx)
                .stream()
                .map(member -> {
                    try {
                        ReservationQueueEntity reservationQueueEntity = objectMapper.readValue(member, ReservationQueueEntity.class);
                        return reservationQueueEntity.toDomain();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public long removeRange(long startIdx, long endIdx) {
        return redisTemplate.opsForZSet().removeRange(REDIS_QUEUE_KEY, startIdx, endIdx);
    }
}
