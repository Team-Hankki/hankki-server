package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.controller.request.UserLoginRequest;
import org.hankki.hankkiserver.api.auth.service.response.UserLoginResponse;
import org.hankki.hankkiserver.api.auth.service.response.UserReissueResponse;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.external.openfeign.apple.AppleClientSecretGenerator;
import org.hankki.hankkiserver.external.openfeign.apple.AppleOAuthProvider;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.hankki.hankkiserver.external.openfeign.kakao.KakaoOAuthProvider;
import org.hankki.hankkiserver.external.openfeign.kakao.dto.KakaoUnlinkRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;
import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.Platform.APPLE;
import static org.hankki.hankkiserver.domain.user.model.Platform.KAKAO;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Value("${oauth.kakao.key}")
    private String adminKey;

    private final UserFinder userFinder;
    private final UserUpdater userUpdater;
    private final UserInfoFinder userInfoFinder;
    private final UserInfoUpdater userInfoUpdater;
    private final UserInfoDeleter userInfoDeleter;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;
    private final AppleClientSecretGenerator appleClientSecretGenerator;

    public UserLoginResponse login(final String token, final UserLoginRequest request) {
        Platform platform = Platform.getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoDto socialInfo = getSocialInfo(token, platform, request.name());
        boolean isRegistered = userFinder.isRegisteredUser(platform, socialInfo);
        User findUser = loadOrCreateUser(platform, socialInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    public void logout(final Long userId) {
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(null);
    }

    public void withdraw(final Long userId, final String code) {
        User user = userFinder.getUser(userId);
        if (APPLE == user.getPlatform()){
            try {
                String clientSecret = appleClientSecretGenerator.generateClientSecret();
                String refreshToken = appleOAuthProvider.getAppleRefreshToken(code, clientSecret);
                appleOAuthProvider.requestRevoke(refreshToken, clientSecret);
            } catch (Exception e) {
                throw new BadRequestException(AuthErrorCode.APPLE_REVOKE_FAILED);
            }
        }
        if (KAKAO == user.getPlatform()) {
            kakaoOAuthProvider.unlinkKakaoServer(adminKey, KakaoUnlinkRequest.of(user.getSerialId()));
        }
        user.softDelete();
        userInfoDeleter.softDelete(userId);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserReissueResponse reissue(final String refreshToken) {
        Long userId = jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return UserReissueResponse.of(issuedTokens);
    }

    private Token generateTokens(final Long userId) {
        Token issuedTokens = jwtProvider.issueTokens(userId, getUserRole(userId));
        UserInfo findUserInfo = userInfoFinder.getUserInfo(userId);
        findUserInfo.updateRefreshToken(issuedTokens.refreshToken());
        return issuedTokens;
    }

    private String getUserRole(final Long userId) {
        return userFinder.getUser(userId).getUserRole().getValue();
    }

    private SocialInfoDto getSocialInfo(final String providerToken, final Platform platform, final String name) {
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
        user.updateDeletedAt(null);
        userInfoFinder.getUserInfo(user.getId()).updateNickname(user.getName());
        userInfoFinder.getUserInfo(user.getId()).updateProfile();
        return user;
    }

    private String getRefreshToken(final Long userId) {
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
