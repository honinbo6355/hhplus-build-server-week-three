package com.example.hhplus.reservation.infrastructure.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenConsumer {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final String REDIS_TOKEN_KEY = "reservation-token";

    @KafkaListener(topics = "token-topic", groupId = "consumer_group01")
    public void consume(String message) throws IOException {
        System.out.printf("Token Consumed Message : %s%n", message);

        Long userId = Long.parseLong(message);

        try {
            Set<String> members = redisTemplate.opsForSet().members(REDIS_TOKEN_KEY);
            ReservationTokenEntity reservationTokenEntity = null;
            for (String member : members) {
                ReservationTokenEntity token = objectMapper.readValue(member, ReservationTokenEntity.class);
                if (token.getUserId().equals(userId)) {
                    reservationTokenEntity = token;
                    break;
                }
            }
            if (reservationTokenEntity == null) {
                throw new NullPointerException();
            }
            redisTemplate.opsForSet().remove(REDIS_TOKEN_KEY, objectMapper.writeValueAsString(reservationTokenEntity));
        } catch (JsonProcessingException e) {
            log.error("{}", e);
        }
    }
}
