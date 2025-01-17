package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.response.UserInfoResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.api.user.service.UserFinder;
import org.hankki.hankkiserver.api.user.service.UserInfoFinder;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final UserFinder userFinder;
    private final UserInfoFinder userInfoFinder;
    private final JwtValidator jwtValidator;
    private final ExternalService externalService;
    private final AuthService authService;

    public UserLoginResponse login(final String token, final UserLoginRequest request) {
        SocialInfoResponse response = externalService.getUserInfo(token, request.platform(), request.name());
        UserInfoResponse userInfoResponse = UserInfoResponse.of(request.platform(), response.serialId(),
                response.name(), response.email());
        return authService.saveOrGetUser(userInfoResponse);
    }

    public void withdraw(final long userId, final String code) {
        User user = userFinder.getUser(userId);
        externalService.revoke(user.getPlatform(), code, user.getSerialId());
        authService.deleteUser(user);
    }

    @Transactional
    public void logout(final long userId) {
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(null);
    }

    @Transactional
    public UserReissueResponse reissue(final String refreshToken) {
        long userId = authService.parseUserId(refreshToken);
        validateRefreshToken(refreshToken, userId);
        Token issuedTokens = authService.generateTokens(userId);
        return UserReissueResponse.of(issuedTokens);
    }

    private void validateRefreshToken(final String refreshToken, final Long userId) {
        jwtValidator.validateRefreshToken(refreshToken);
        String storedRefreshToken = userInfoFinder.getUserInfo(userId).getRefreshToken();
        jwtValidator.checkTokenEquality(refreshToken, storedRefreshToken);
    }
}
