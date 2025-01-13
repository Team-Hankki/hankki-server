package org.hankki.hankkiserver.api.auth.service;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.response.UserInfoResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.UserStatus;
import org.hankki.hankkiserver.event.EventPublisher;
import org.hankki.hankkiserver.event.user.CreateUserEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserFinder userFinder;
    private final UserUpdater userUpdater;
    private final UserInfoFinder userInfoFinder;
    private final UserInfoUpdater userInfoUpdater;
    private final JwtProvider jwtProvider;
    private final EventPublisher eventPublisher;

    private static final String NONE = null;

    @Transactional
    public UserLoginResponse saveOrGetUser(final UserInfoResponse userInfo) {
        Optional<User> user = userFinder.findUserByPlatFormAndSeralId(userInfo.platform(), userInfo.serialId());
        boolean isRegistered = isRegistered(user);
        User findUser = loadOrCreateUser(user, userInfo.platform(), userInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    protected Token generateTokens(final long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    protected long parseUserId(final String refreshToken) {
        return jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
    }

    private boolean isRegistered(final Optional<User> user) {
        return user.map(u -> u.getStatus() == ACTIVE)
                .orElse(false);
    }

    private User loadOrCreateUser(final Optional<User> findUser, final Platform platform,
                                  final UserInfoResponse userInfo) {
        return findUser.map(user -> updateOrGetUserInfo(user, user.getStatus(), userInfo))
                .orElseGet(() -> createNewUser(userInfo, platform));
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
        eventPublisher.publish(
                CreateUserEvent.of(newUser.getId(), newUser.getName(), newUser.getPlatform().toString()));
        return newUser;
    }

    private String getUserRole(final long userId) {
        return userFinder.getUser(userId).getRole().getValue();
    }

    private void saveUserAndUserInfo(final User user) {
        userUpdater.saveUser(user);
        UserInfo userInfo = UserInfo.createMemberInfo(user, NONE);
        userInfoUpdater.saveUserInfo(userInfo);
    }
}
