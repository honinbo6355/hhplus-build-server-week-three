package com.example.hhplus.reservation.api.sample;

import com.example.hhplus.reservation.domain.user.ReservationQueueStatus;
import com.example.hhplus.reservation.domain.user.ReservationToken;
import com.example.hhplus.reservation.infrastructure.user.ReservationQueueEntity;
import com.example.hhplus.reservation.infrastructure.user.ReservationTokenEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sample")
public class SampleController {
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/set")
    public ResponseEntity<?> setKeyValue() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("Korea", "Seoul");
        vop.set("America", "NewYork");
        vop.set("Italy", "Rome");
        vop.set("Japan", "Tokyo");
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<?> getValueFromKey(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @PostMapping("/set/sortedSet")
    public ResponseEntity<?> setSortedSet(@RequestParam List<Long> userIds) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        for (Long userId : userIds) {
            ReservationQueueEntity reservationQueueEntity = new ReservationQueueEntity(userId);
            zSetOperations.add("reservation-queue", objectMapper.writeValueAsString(reservationQueueEntity), Instant.now().toEpochMilli());
        }
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @GetMapping("/get/sortedSet")
    public ResponseEntity<?> getSortedSet() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        ZSetOperations.TypedTuple<String> typedTuple = zSetOperations.popMax("reservation-queue");
        ReservationQueueEntity reservationQueueEntity = objectMapper.readValue(typedTuple.getValue(), ReservationQueueEntity.class);
        System.out.println(reservationQueueEntity);
        return new ResponseEntity<>(reservationQueueEntity, HttpStatus.OK);
    }

    @PostMapping("/set/set")
    public ResponseEntity<?> setSet(@RequestParam List<Long> userIds) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        for (Long userId : userIds) {
            ReservationTokenEntity reservationTokenEntity = new ReservationTokenEntity(UUID.randomUUID().toString(), userId, LocalDateTime.now());
            setOperations.add("reservation-token", objectMapper.writeValueAsString(reservationTokenEntity));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
