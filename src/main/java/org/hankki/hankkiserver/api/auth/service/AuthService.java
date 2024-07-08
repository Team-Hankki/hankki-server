package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.common.exception.EntityNotFoundException;
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

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.INACTIVE;
import static org.hankki.hankkiserver.domain.user.model.Platform.APPLE;
import static org.hankki.hankkiserver.domain.user.model.Platform.KAKAO;
import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserAdapter userAdapter;
    private final UserInfoAdapter userInfoAdapter;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;

    public UserLoginResponse login(
            final String token,
            final UserLoginRequest request) {
        Platform platform = request.platform();
        SocialInfoDto socialInfo = getSocialInfo(token, platform, request.name());
        boolean isRegistered = userAdapter.isRegisteredUser(platform, socialInfo);
        User findUser = loadOrCreateUser(platform, socialInfo, isRegistered);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    public void logOut(final long userId) {
        UserInfo findUserInfo = userInfoAdapter.getUserInfo(userId);
        updateRefreshToken(null, findUserInfo);
    }

    public void withdraw(final long userId, final String code) {
        User user = userAdapter.getUser(userId);
        if (APPLE == user.getPlatform()){
            try {
                String refreshToken = appleOAuthProvider.getAppleToken(code);
                appleOAuthProvider.requestRevoke(refreshToken);
            } catch (Exception e) {
                throw new InvalidValueException(ErrorCode.APPLE_REVOKE_FAILED);
            }
        }
        user.softDelete(INACTIVE);
        userInfoAdapter.softDelete(userId);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserReissueResponse reissue(final String refreshToken) {
        long userId = jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = userInfoAdapter.getUserInfo(userId);
        Token issueTokens = jwtProvider.issueTokens(userId);
        updateRefreshToken(issueTokens.refreshToken(), findUserInfo);
        return UserReissueResponse.of(issueTokens);
    }

    private Token generateTokens(final long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId);
        UserInfo findUserInfo = userInfoAdapter.getUserInfo(userId);
        updateRefreshToken(issuedTokens.refreshToken(), findUserInfo);
        return issuedTokens;
    }

    private SocialInfoDto getSocialInfo(
            final String providerToken,
            final Platform platform,
            final String name) {
        if (KAKAO == platform){
            return kakaoOAuthProvider.getKakaoUserInfo(providerToken);
        } else if (APPLE == platform){
            return appleOAuthProvider.getAppleUserInfo(providerToken, name);
        }
        throw new InvalidValueException(ErrorCode.INVALID_PLATFORM_TYPE);
    }

    private User loadOrCreateUser(Platform platform, SocialInfoDto socialInfo, boolean isRegistered){
        if (!isRegistered){
            User newUser = User.createUser(
                    socialInfo.name(),
                    socialInfo.email(),
                    socialInfo.serialId(),
                    platform);
            saveUser(newUser);
            return newUser;
        }
        return userAdapter.getUser(platform, socialInfo.serialId());
    }

    private String getRefreshToken(final Long userId) {
        return userInfoAdapter.getUserInfo(userId)
                .getRefreshToken();
    }

    private void saveUser(final User user) {
        userAdapter.saveUser(user);
        UserInfo userInfo = UserInfo.createMemberInfo(user, null);
        userInfoAdapter.saveUserInfo(userInfo);
    }

    private void updateRefreshToken(final String refreshToken, final UserInfo userInfo) {
        userInfo.updateRefreshToken(refreshToken);
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
