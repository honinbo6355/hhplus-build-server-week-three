package com.example.hhplus.reservation.api.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/push")
public class PushMockController {

    @PostMapping
    public ResponseEntity<String> sendPush() {
        String result = "send push completed";
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
