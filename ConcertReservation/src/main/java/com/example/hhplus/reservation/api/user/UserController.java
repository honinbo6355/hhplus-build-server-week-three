package com.example.hhplus.reservation.api.user;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.user.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/token")
    public ResponseEntity<BasicResponse<TokenResponse>> createToken(@RequestBody TokenRequest tokenRequest) {
        return null;
    }

    @GetMapping("/token")
    public ResponseEntity<BasicResponse<TokenDetailResponse>> getTokenDetail(@RequestHeader String userUuid) {
        return null;
    }

    @PatchMapping("/point")
    public ResponseEntity<BasicResponse<PointChargeResponse>> chargePoint(@RequestBody PointChargeRequest pointChargeRequest) {
        return null;
    }

    @GetMapping("/{userId}/point")
    public ResponseEntity<BasicResponse<PointResponse>> getPoint(@PathVariable Long userId) {
        return null;
    }
}
