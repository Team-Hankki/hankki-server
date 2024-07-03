package org.hankki.hankkiserver.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.ApiResponse;
import org.hankki.hankkiserver.common.BaseResponse;
import org.hankki.hankkiserver.common.dto.SuccessMessage;
import org.hankki.hankkiserver.service.AuthService;
import org.hankki.hankkiserver.service.dto.request.UserLoginRequest;
import org.hankki.hankkiserver.service.dto.request.UserReissueRequest;
import org.hankki.hankkiserver.service.dto.response.UserLoginResponse;
import org.hankki.hankkiserver.service.dto.response.UserReissueResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    private final AuthService authService;

    @PostMapping("/users/login")
    public ResponseEntity<BaseResponse<?>> login(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
            @RequestBody final UserLoginRequest request) {
        final UserLoginResponse response = authService.login(token, request);
        return ApiResponse.success(SuccessMessage.OK, response);
    }

    @PatchMapping("/users/signout")
    public ResponseEntity<BaseResponse<?>> signOut(
            @UserId final Long userId) {
        authService.signOut(userId);
        return ApiResponse.success(SuccessMessage.OK);
    }

    @DeleteMapping("/users/withdraw")
    public ResponseEntity<BaseResponse<?>> withdraw(
            @UserId final Long userId) {
        authService.withdraw(userId);
        return ApiResponse.success(SuccessMessage.OK);
    }

    @PostMapping("/users/reissue")
    public ResponseEntity<BaseResponse<?>> reissue(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String refreshtoken,
            @RequestBody final UserReissueRequest request) {
        final UserReissueResponse response = authService.reissue(refreshtoken, request);
        return ApiResponse.success(SuccessMessage.OK, response);
    }
}
