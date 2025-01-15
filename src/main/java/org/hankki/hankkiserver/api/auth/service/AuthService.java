package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.response.UserInfoResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFinder userFinder;
    private final UserInfoFinder userInfoFinder;
    private final JwtValidator jwtValidator;
    private final OAuthProviderFactory oAuthProviderFactory;
    private final AuthFacade authFacade;

    public UserLoginResponse login(final String token, final UserLoginRequest request) {
        Platform platform = Platform.getEnumPlatformFromStringPlatform(request.platform());
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        SocialInfoResponse response = oAuthProvider.getUserInfo(token, request.name());
        UserInfoResponse userInfoResponse = UserInfoResponse.of(platform, response.serialId(), response.name(), response.email());
        return authFacade.saveOrGetUser(userInfoResponse);
    }

    public void withdraw(final long userId, final String code) {
        User user = userFinder.getUser(userId);
        Platform platform = user.getPlatform();
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        oAuthProvider.requestRevoke(code, user.getSerialId());
        authFacade.deleteUser(user);
    }

    @Transactional
    public void logout(final long userId) {
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(null);
    }

    @Transactional
    public UserReissueResponse reissue(final String refreshToken) {
        long userId = authFacade.parseUserId(refreshToken);
        validateRefreshToken(refreshToken, userId);
        Token issuedTokens = authFacade.generateTokens(userId);
        return UserReissueResponse.of(issuedTokens);
    }

    private void validateRefreshToken(final String refreshToken, final Long userId) {
        jwtValidator.validateRefreshToken(refreshToken);
        String storedRefreshToken = getRefreshToken(userId);
        jwtValidator.equalsRefreshToken(refreshToken, storedRefreshToken);
    }

    private String getRefreshToken(final long userId) {
        return userInfoFinder.getUserInfo(userId).getRefreshToken();
    }
}
