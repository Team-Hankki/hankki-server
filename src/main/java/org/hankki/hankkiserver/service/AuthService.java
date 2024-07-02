package org.hankki.hankkiserver.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.domain.User;
import org.hankki.hankkiserver.domain.UserInfo;
import org.hankki.hankkiserver.domain.Platform;
import org.hankki.hankkiserver.exception.EntityNotFoundException;
import org.hankki.hankkiserver.exception.UnauthorizedException;
import org.hankki.hankkiserver.oauth.kakao.dto.SocialInfoDto;
import org.hankki.hankkiserver.oauth.kakao.KakaoOAuthProvider;
import org.hankki.hankkiserver.repository.UserInfoRepository;
import org.hankki.hankkiserver.repository.UserRepository;
import org.hankki.hankkiserver.service.dto.request.UserLoginRequest;
import org.hankki.hankkiserver.service.dto.request.UserReissueRequest;
import org.hankki.hankkiserver.service.dto.response.UserLoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hankki.hankkiserver.domain.User.createUser;
import static org.hankki.hankkiserver.domain.UserInfo.createMemberInfo;
import static org.hankki.hankkiserver.domain.Platform.KAKAO;
import static org.hankki.hankkiserver.domain.Platform.getEnumPlatformFromStringPlatform;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;

    public UserLoginResponse login(
            final String token,
            final UserLoginRequest request) {
        Platform platform = getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoDto socialInfo = getSocialInfo(token, platform);
        User findUser = loadOrCreateUser(platform, socialInfo);
        Token issuedToken = generateTokens(findUser.getId());
        return UserLoginResponse.of(issuedToken);
    }

    public void signOut(final Long userId) {
        UserInfo findUserInfo = getUserInfo(userId);
        updateRefreshToken(null, findUserInfo);
    }

    public void withdraw(final Long userId) {
        userRepository.deleteById(userId);
        userInfoRepository.deleteByUserId(userId);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserLoginResponse reissue(
            final String refreshToken,
            final UserReissueRequest request) {
        Long userId = request.userId();
        validateRefreshToken(refreshToken, userId);
        UserInfo findUserInfo = getUserInfo(userId);
        Token issueToken = jwtProvider.issueToken(userId);
        updateRefreshToken(issueToken.refreshToken(), findUserInfo);
        return UserLoginResponse.of(issueToken);
    }

    private Token generateTokens(final Long userId) {
        Token issuedToken = jwtProvider.issueToken(userId);
        jwtProvider.issueToken(userId);
        UserInfo findUserInfo = getUserInfo(userId);
        updateRefreshToken(issuedToken.refreshToken(), findUserInfo);
        return issuedToken;
    }

    private SocialInfoDto getSocialInfo(
            final String providerToken,
            final Platform platform) {
        if (platform == KAKAO){
            return kakaoOAuthProvider.getUserInfo(providerToken);
        }
        return null; // appleLoginService.getInfo(providerToken);
    }

    private User loadOrCreateUser(Platform platform, SocialInfoDto socialInfo){
        boolean isRegistered = userRepository.existsByPlatformAndSerialId(
                platform,
                socialInfo.serialId());
        if (!isRegistered){
            User newUser = createUser(
                    socialInfo.name(),
                    socialInfo.email(),
                    socialInfo.serialId(),
                    platform);
            saveMember(newUser);
        }
        return getUser(platform, socialInfo.serialId());
    }

    private User getUser(
            final Platform platform,
            final String serialId) {
        return userRepository.findByPlatformAndSerialId(platform, serialId)
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

    private void saveMember(final User user) {
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
            signOut(userId);
            throw e;
        }
    }
}
