package org.hankki.hankkiserver.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.exception.EntityNotFoundException;
import org.hankki.hankkiserver.exception.InvalidValueException;
import org.hankki.hankkiserver.exception.UnauthorizedException;
import org.hankki.hankkiserver.oauth.apple.AppleOAuthProvider;
import org.hankki.hankkiserver.oauth.dto.SocialInfoDto;
import org.hankki.hankkiserver.oauth.kakao.KakaoOAuthProvider;
import org.hankki.hankkiserver.domain.user.repository.UserInfoRepository;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.hankki.hankkiserver.service.dto.request.UserLoginRequest;
import org.hankki.hankkiserver.service.dto.response.UserLoginResponse;
import org.hankki.hankkiserver.service.dto.response.UserReissueResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.MemberStatus.INACTIVE;
import static org.hankki.hankkiserver.domain.user.model.Platform.*;
import static org.hankki.hankkiserver.domain.user.model.User.createUser;
import static org.hankki.hankkiserver.domain.user.model.UserInfo.createMemberInfo;
import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;

    public UserLoginResponse login(
            final String token,
            final UserLoginRequest request) {
        Platform platform = getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoDto socialInfo = getSocialInfo(token, platform, request.name());
        boolean isRegistered = isRegisteredUser(platform, socialInfo);
        User findUser = loadOrCreateUser(platform, socialInfo, isRegistered);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken, isRegistered);
    }

    public void logOut(final Long userId) {
        UserInfo findUserInfo = getUserInfo(userId);
        updateRefreshToken(null, findUserInfo);
    }

    public void withdraw(final Long userId, final String code) {
        User user = getUser(userId);
        if (user.getPlatform() == APPLE){
            try {
                String refreshToken = appleOAuthProvider.getAppleToken(code);
                appleOAuthProvider.requestRevoke(refreshToken);
            } catch (Exception e) {
                throw new InvalidValueException(ErrorMessage.APPLE_REVOKE_FAILED);
            }
        }

        userRepository.softDeleteById(userId, INACTIVE);
        userInfoRepository.softDeleteByUserId(userId);
        user.softDelete(INACTIVE);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserReissueResponse reissue(final String refreshToken) {
        Long userId = jwtProvider.getSubject(refreshToken.substring(BEARER.length()));
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = getUserInfo(userId);
        Token issueToken = jwtProvider.issueToken(userId);
        updateRefreshToken(issueToken.refreshToken(), findUserInfo);
        return UserReissueResponse.of(issueToken);
    }

    private Token generateTokens(final Long userId) {
        Token issuedToken = jwtProvider.issueToken(userId);
        UserInfo findUserInfo = getUserInfo(userId);
        updateRefreshToken(issuedToken.refreshToken(), findUserInfo);
        return issuedToken;
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
        throw new EntityNotFoundException(ErrorMessage.INVALID_PLATFORM_TYPE);
    }

    private boolean isRegisteredUser(Platform platform, SocialInfoDto socialInfo){
        return userRepository.existsByPlatformAndSerialIdAndMemberStatus(
                platform,
                socialInfo.serialId(),
                ACTIVE);
    }

    private User loadOrCreateUser(Platform platform, SocialInfoDto socialInfo, boolean isRegistered){
        if (!isRegistered){
            User newUser = createUser(
                    socialInfo.name(),
                    socialInfo.email(),
                    socialInfo.serialId(),
                    platform);
            saveUser(newUser);
            return newUser;
        }

        User findUser = getUser(platform, socialInfo.serialId());
        if (INACTIVE == findUser.getMemberStatus()) {
            findUser.softDelete(ACTIVE);
            userRepository.save(findUser);
        }
        return findUser;
    }

    private User getUser(
            final Platform platform,
            final String serialId) {
        return userRepository.findByPlatformAndSerialId(platform, serialId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    private User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    private UserInfo getUserInfo(final Long memberId) {
        return userInfoRepository.findByUserId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_INFO_NOT_FOUND));
    }

    private String getRefreshToken(final Long userId) {
        return userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND))
                .getRefreshToken();
    }

    private void saveUser(final User user) {
        userRepository.save(user);
        UserInfo userInfo = createMemberInfo(user, null);
        userInfoRepository.save(userInfo);
    }

    private void updateRefreshToken(
            final String refreshToken,
            final UserInfo userInfo) {
        userInfo.updateRefreshToken(refreshToken);
    }

    private void validateRefreshToken(
            final String refreshToken,
            final Long userId) {
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
