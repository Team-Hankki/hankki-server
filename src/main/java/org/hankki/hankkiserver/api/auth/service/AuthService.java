package org.hankki.hankkiserver.api.auth.service;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.UserStatus;
import org.hankki.hankkiserver.event.EventPublisher;
import org.hankki.hankkiserver.event.user.CreateUserEvent;
import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFinder userFinder;
    private final UserUpdater userUpdater;
    private final UserInfoFinder userInfoFinder;
    private final UserInfoUpdater userInfoUpdater;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final OAuthProviderFactory oAuthProviderFactory;
    private final EventPublisher eventPublisher;

    @Transactional
    public UserLoginResponse login(final String token, final UserLoginRequest request) {
        Platform platform = Platform.getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoResponse socialInfo = getSocialInfo(token, platform, request.name());
        Optional<User> user = userFinder.findUserByPlatFormAndSeralId(platform, socialInfo.serialId());
        boolean isRegistered = isRegistered(user);
        User findUser = loadOrCreateUser(user, platform, socialInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    @Transactional
    public void logout(final long userId) {
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(null);
    }

    @Transactional
    public void withdraw(final long userId, final String code) {
        User user = userFinder.getUser(userId);
        Platform platform = user.getPlatform();
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        oAuthProvider.requestRevoke(code, user.getSerialId());
        user.softDelete();
        userInfoFinder.getUserInfo(userId).softDelete();
    }

    @Transactional
    public UserReissueResponse reissue(final String refreshToken) {
        Long userId = jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return UserReissueResponse.of(issuedTokens);
    }

    private Token generateTokens(final long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    private String getUserRole(final long userId) {
        return userFinder.getUser(userId).getRole().getValue();
    }

    private boolean isRegistered(final Optional<User> user) {
        return user.map(u -> u.getStatus() == ACTIVE)
                .orElse(false);
    }

    private SocialInfoResponse getSocialInfo(final String providerToken, final Platform platform, final String name) {
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        return oAuthProvider.getUserInfo(providerToken, name);
    }

    private User loadOrCreateUser(final Optional<User> findUser, final Platform platform, final SocialInfoResponse socialInfo) {
        return findUser.map(user -> updateOrGetUserInfo(user, user.getStatus(), socialInfo))
                .orElseGet(() -> createNewUser(socialInfo, platform));
    }

    private User updateOrGetUserInfo(final User user, final UserStatus status, final SocialInfoResponse socialInfo) {
        if (status == ACTIVE) {
            return user;
        }
        return updateUserInfo(user, socialInfo);
    }

    private User updateUserInfo(final User user, final SocialInfoResponse socialInfo) {
        user.rejoin(socialInfo);
        userInfoFinder.getUserInfo(user.getId()).updateNickname(socialInfo.name());
        return user;
    }

    private User createNewUser(final SocialInfoResponse socialInfo, final Platform platform) {
        User newUser = createUser(socialInfo.name(), socialInfo.email(), socialInfo.serialId(), platform);
        saveUserAndUserInfo(newUser);
        eventPublisher.publish(CreateUserEvent.of(newUser.getId(), newUser.getName(), newUser.getPlatform().toString()));
        return newUser;
    }

    private String getRefreshToken(final long userId) {
        return userInfoFinder.getUserInfo(userId).getRefreshToken();
    }

    private void saveUserAndUserInfo(final User user) {
        userUpdater.saveUser(user);
        UserInfo userInfo = UserInfo.createMemberInfo(user, null);
        userInfoUpdater.saveUserInfo(userInfo);
    }

    private void validateRefreshToken(final String refreshToken, final Long userId) {
        try {
            jwtValidator.validateRefreshToken(refreshToken);
            String storedRefreshToken = getRefreshToken(userId);
            jwtValidator.equalsRefreshToken(refreshToken, storedRefreshToken);
        } catch (UnauthorizedException e) {
            logout(userId);
            throw e;
        }
    }
}
