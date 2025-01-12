package org.hankki.hankkiserver.api.auth.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.AuthService;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.api.common.annotation.UserId;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public HankkiResponse<UserLoginResponse> login(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
                                                   @Valid @RequestBody final UserLoginRequest request) {
        UserLoginResponse response = authService.login(token, request);
        return HankkiResponse.success(CommonSuccessCode.OK, response);
    }

    @PatchMapping("/auth/logout")
    public HankkiResponse<Void> signOut(@UserId final Long userId) {
        authService.logout(userId);
        return HankkiResponse.success(CommonSuccessCode.OK);
    }

    @DeleteMapping("/auth/withdraw")
    public HankkiResponse<Void> withdraw(@UserId final Long userId,
                                         @Nullable @RequestHeader("X-Apple-Code") final String code) {
        authService.withdraw(userId, code);
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }

    @PostMapping("/auth/reissue")
    public HankkiResponse<UserReissueResponse> reissue(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String refreshToken) {
        UserReissueResponse response = authService.reissue(refreshToken);
        return HankkiResponse.success(CommonSuccessCode.OK, response);
    }
}
