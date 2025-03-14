package org.hankki.hankkiserver.api.auth.service;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.response.UserInfoResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.user.service.UserFinder;
import org.hankki.hankkiserver.api.user.service.UserInfoFinder;
import org.hankki.hankkiserver.api.user.service.UserInfoUpdater;
import org.hankki.hankkiserver.api.user.service.UserUpdater;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.UserStatus;
import org.hankki.hankkiserver.event.EventPublisher;
import org.hankki.hankkiserver.event.user.CreateUserEvent;
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
    private final EventPublisher eventPublisher;

    @Transactional
    public UserLoginResponse saveOrGetUser(final UserInfoResponse userInfo) {
        Optional<User> user = userFinder.findUserByPlatFormAndSeralId(userInfo.platform(), userInfo.serialId());
        boolean isRegistered = isRegistered(user);
        User findUser = loadOrCreateUser(user, userInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    @Transactional
    public void deleteUser(final User user) {
        user.softDelete();
        userInfoFinder.getUserInfo(user.getId()).softDelete();
    }

    protected Token generateNewTokens(final String refreshToken) {
        String strippedToken = refreshToken.substring(BEARER.length());
        long userId = jwtProvider.getSubject(strippedToken);
        validateRefreshToken(refreshToken, userId);
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    private Token generateTokens(final long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    private void validateRefreshToken(final String refreshToken, final long userId) {
        jwtValidator.validateRefreshToken(refreshToken);
        String storedRefreshToken = userInfoFinder.getUserInfo(userId).getRefreshToken();
        jwtValidator.checkTokenEquality(refreshToken, storedRefreshToken);
    }

    private boolean isRegistered(final Optional<User> user) {
        return user.map(u -> u.getStatus() == ACTIVE)
                .orElse(false);
    }

    private User loadOrCreateUser(final Optional<User> findUser, final UserInfoResponse userInfo) {
        return findUser.map(user -> updateOrGetUserInfo(user, user.getStatus(), userInfo))
                .orElseGet(() -> createNewUser(userInfo, userInfo.platform()));
    }

    private User updateOrGetUserInfo(final User user, final UserStatus status, final UserInfoResponse userInfo) {
        if (status == ACTIVE) {
            return user;
        }
        return updateUserInfo(user, userInfo);
    }

    private User updateUserInfo(final User user, final UserInfoResponse userInfo) {
        user.rejoin(userInfo.name(), userInfo.email());
        userInfoFinder.getUserInfo(user.getId()).updateNickname(userInfo.name());
        return user;
    }

    private User createNewUser(final UserInfoResponse userInfo, final Platform platform) {
        User newUser = createUser(userInfo.name(), userInfo.email(), userInfo.serialId(), platform);
        saveUserAndUserInfo(newUser);
        eventPublisher.publish(CreateUserEvent.of(newUser.getId(), newUser.getName(), newUser.getPlatform().toString()));
        return newUser;
    }

    private String getUserRole(final long userId) {
        return userFinder.getUser(userId).getRole().getValue();
    }

    private void saveUserAndUserInfo(final User user) {
        userUpdater.saveUser(user);
        UserInfo userInfo = UserInfo.createMemberInfo(user, null);
        userInfoUpdater.saveUserInfo(userInfo);
    }
}
