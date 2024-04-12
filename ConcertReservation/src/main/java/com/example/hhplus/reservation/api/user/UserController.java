package com.example.hhplus.reservation.api.user;

import com.example.hhplus.reservation.api.common.BasicResponse;
import com.example.hhplus.reservation.api.user.dto.*;
import com.example.hhplus.reservation.domain.user.User;
import com.example.hhplus.reservation.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<BasicResponse<TokenResponse>> createToken(@RequestBody TokenRequest tokenRequest) {
        return null;
    }

    @GetMapping("/token")
    public ResponseEntity<BasicResponse<TokenDetailResponse>> getTokenDetail(@RequestHeader String token) {
        return null;
    }

    @PatchMapping("/point")
    public ResponseEntity<BasicResponse<PointChargeResponse>> chargePoint(@RequestBody PointChargeRequest pointChargeRequest) {
        PointChargeResponse pointChargeResponse = userService.chargePoint(pointChargeRequest.userId(), pointChargeRequest.point());
        return new ResponseEntity<>(new BasicResponse<>(pointChargeResponse, null), HttpStatus.OK);
    }

    @GetMapping("/{userId}/point")
    public ResponseEntity<BasicResponse<PointResponse>> getPoint(@PathVariable Long userId) {
        User user = userService.getPoint(userId);
        return new ResponseEntity<>(new BasicResponse<>(new PointResponse(user.getId(), user.getAmount()), null), HttpStatus.OK);
    }
}
