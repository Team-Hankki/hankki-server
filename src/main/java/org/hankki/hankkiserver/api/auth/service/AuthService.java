package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.common.exception.InvalidValueException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.external.openfeign.apple.AppleOAuthProvider;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.external.openfeign.kakao.KakaoOAuthProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.Platform.APPLE;
import static org.hankki.hankkiserver.domain.user.model.Platform.KAKAO;
import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserFinder userFinder;
    private final UserSaver userSaver;
    private final UserInfoFinder userInfoFinder;
    private final UserInfoSaver userInfoSaver;
    private final UserInfoDeleter userInfoDeleter;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;

    public UserLoginResponse login(
            final String token,
            final UserLoginRequest request) {
        Platform platform = Platform.getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoDto socialInfo = getSocialInfo(token, platform, request.name());
        boolean isRegistered = userFinder.isRegisteredUser(platform, socialInfo);
        User findUser = loadOrCreateUser(platform, socialInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    public void logOut(final long userId) {
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(null);
    }

    public void withdraw(final long userId, final String code) {
        User user = userFinder.getUser(userId);
        if (APPLE == user.getPlatform()){
            try {
                String refreshToken = appleOAuthProvider.getAppleToken(code);
                appleOAuthProvider.requestRevoke(refreshToken);
            } catch (Exception e) {
                throw new InvalidValueException(AuthErrorCode.APPLE_REVOKE_FAILED);
            }
        }
        user.softDelete();
        userInfoDeleter.softDelete(userId);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserReissueResponse reissue(final String refreshToken) {
        long userId = jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        Token issuedTokens = jwtProvider.issueTokens(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return UserReissueResponse.of(issuedTokens);
    }

    private Token generateTokens(final long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId);
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    private SocialInfoDto getSocialInfo(
            final String providerToken,
            final Platform platform,
            final String name) {
        if (KAKAO == platform){
            return kakaoOAuthProvider.getKakaoUserInfo(providerToken);
        }
        return appleOAuthProvider.getAppleUserInfo(providerToken, name);
    }

    private User loadOrCreateUser(final Platform platform, final SocialInfoDto socialInfo) {
        return userFinder.findUserByPlatFormAndSeralId(platform, socialInfo.serialId())
                .map(this::updateUserInfo)
                .orElseGet(() -> {
                    User newUser = createUser(
                            socialInfo.name(),
                            socialInfo.email(),
                            socialInfo.serialId(),
                            platform);
                    saveUserAndUserInfo(newUser);
                    return newUser;
                });
    }


    private User updateUserInfo(final User user) {
        user.updateStatus(ACTIVE);
        userInfoFinder.getUserInfo(user.getId()).updateNickname(user.getName());
        return user;
    }

    private String getRefreshToken(final Long userId) {
        return userInfoFinder.getUserInfo(userId)
                .getRefreshToken();
    }

    private void saveUserAndUserInfo(final User user) {
        userSaver.saveUser(user);
        UserInfo userInfo = UserInfo.createMemberInfo(user, null);
        userInfoSaver.saveUserInfo(userInfo);
    }

    private void validateRefreshToken(final String refreshToken, final long userId) {
        try {
            jwtValidator.validateRefreshToken(refreshToken);
            String storedRefreshToken = getRefreshToken(userId);
            jwtValidator.equalsRefreshToken(refreshToken, storedRefreshToken);
        } catch (UnauthorizedException e) {
            logOut(userId);
            throw e;
        }
    }
}
