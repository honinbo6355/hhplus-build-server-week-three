package com.example.hhplus.reservation.infrastructure.user;

import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.example.hhplus.reservation.domain.user.ReservationTokenRepository;
import com.example.hhplus.reservation.domain.user.ReservationTokenStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ReservationTokenRepositoryImpl implements ReservationTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final String REDIS_TOKEN_KEY = "reservation-token";

    @Override
    public ReservationToken save(ReservationToken reservationToken) throws JsonProcessingException {
        redisTemplate.opsForSet().add(REDIS_TOKEN_KEY, objectMapper.writeValueAsString(ReservationTokenEntity.toEntity(reservationToken)));
        return reservationToken;
    }

    @Override
    public ReservationToken remove(ReservationToken reservationToken) throws JsonProcessingException {
        redisTemplate.opsForSet().remove(REDIS_TOKEN_KEY, objectMapper.writeValueAsString(ReservationTokenEntity.toEntity(reservationToken)));
        return reservationToken;
    }

    @Override
    public Optional<ReservationToken> findByUserId(long userId) throws JsonProcessingException {
        Set<String> members = redisTemplate.opsForSet().members(REDIS_TOKEN_KEY);
        for (String member : members) {
            ReservationTokenEntity reservationTokenEntity = objectMapper.readValue(member, ReservationTokenEntity.class);
            if (reservationTokenEntity.getUserId().equals(userId)) {
                return Optional.of(reservationTokenEntity.toDomain());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<ReservationToken> findByToken(String token) throws JsonProcessingException {
        Set<String> members = redisTemplate.opsForSet().members(REDIS_TOKEN_KEY);
        for (String member : members) {
            ReservationTokenEntity reservationTokenEntity = objectMapper.readValue(member, ReservationTokenEntity.class);
            if (reservationTokenEntity.getTokenValue().equals(token)) {
                return Optional.of(reservationTokenEntity.toDomain());
            }
        }

        return Optional.empty();
    }

    @Override
    public long expireDurationOverToken() throws JsonProcessingException {
        Set<String> members = redisTemplate.opsForSet().members(REDIS_TOKEN_KEY);
        List<String> removeMembers = new ArrayList<>();

        for (String member : members) {
            ReservationTokenEntity reservationTokenEntity = objectMapper.readValue(member, ReservationTokenEntity.class);
            if (reservationTokenEntity.isExpired()) {
                removeMembers.add(objectMapper.writeValueAsString(reservationTokenEntity));
            }
        }

        if (removeMembers.size() > 0) {
            return redisTemplate.opsForSet().remove(REDIS_TOKEN_KEY, removeMembers.toArray());
        } else {
            return 0L;
        }
    }

    @Override
    public long countToken() {
        return redisTemplate.opsForSet().size(REDIS_TOKEN_KEY);
    }
}
