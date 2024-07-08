package org.hankki.hankkiserver.api.auth.controller;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.ApiResponse;
import org.hankki.hankkiserver.common.BaseResponse;
import org.hankki.hankkiserver.common.code.SuccessCode;
import org.hankki.hankkiserver.api.auth.service.AuthService;
import org.hankki.hankkiserver.service.dto.request.UserLoginRequest;
import org.hankki.hankkiserver.service.dto.response.UserLoginResponse;
import org.hankki.hankkiserver.service.dto.response.UserReissueResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponse<?>> login(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
            @RequestBody final UserLoginRequest request) {
        final UserLoginResponse response = authService.login(token, request);
        return ApiResponse.success(SuccessCode.OK, response);
    }

    @PatchMapping("/auth/logout")
    public ResponseEntity<BaseResponse<?>> signOut(
            @UserId final Long userId) {
        authService.logOut(userId);
        return ApiResponse.success(SuccessCode.OK);
    }

    @DeleteMapping("/auth/withdraw")
    public ResponseEntity<BaseResponse<?>> withdraw(
            @UserId final Long userId,
            @Nullable @RequestHeader("X-Apple-Code") final String code){
        authService.withdraw(userId,code);
        return ApiResponse.success(SuccessCode.NO_CONTENT);
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<BaseResponse<?>> reissue(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String refreshtoken) {
        final UserReissueResponse response = authService.reissue(refreshtoken);
        return ApiResponse.success(SuccessCode.OK, response);
    }
}
