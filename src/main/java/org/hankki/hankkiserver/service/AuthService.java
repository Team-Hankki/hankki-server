package org.hankki.hankkiserver.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.auth.jwt.Token;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.domain.Member;
import org.hankki.hankkiserver.domain.MemberInfo;
import org.hankki.hankkiserver.domain.Platform;
import org.hankki.hankkiserver.exception.EntityNotFoundException;
import org.hankki.hankkiserver.exception.UnauthorizedException;
import org.hankki.hankkiserver.oauth.kakao.dto.SocialInfoDto;
import org.hankki.hankkiserver.oauth.kakao.KakaoOAuthProvider;
import org.hankki.hankkiserver.repository.MemberInfoRepository;
import org.hankki.hankkiserver.repository.MemberRepository;
import org.hankki.hankkiserver.service.dto.request.UserLoginRequest;
import org.hankki.hankkiserver.service.dto.request.UserReissueRequest;
import org.hankki.hankkiserver.service.dto.response.UserLoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hankki.hankkiserver.domain.Member.createUser;
import static org.hankki.hankkiserver.domain.MemberInfo.createMemberInfo;
import static org.hankki.hankkiserver.domain.Platform.KAKAO;
import static org.hankki.hankkiserver.domain.Platform.getEnumPlatformFromStringPlatform;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final KakaoOAuthProvider kakaoOAuthProvider;

    public UserLoginResponse login(
            final String token,
            final UserLoginRequest request) {
        Platform platform = getEnumPlatformFromStringPlatform(request.platform());
        SocialInfoDto socialInfo = getSocialInfo(token, platform);
        Member findMember = loadOrCreateUser(platform, socialInfo);
        Token issuedToken = generateTokens(findMember.getId());
        return UserLoginResponse.of(issuedToken);
    }

    public void signOut(final Long userId) {
        MemberInfo findMemberInfo = getUserInfo(userId);
        updateRefreshToken(null, findMemberInfo);
    }

    public void withdraw(final Long userId) {
        memberRepository.deleteById(userId);
        memberInfoRepository.deleteByMemberId(userId);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserLoginResponse reissue(
            final String refreshToken,
            final UserReissueRequest request) {
        Long userId = request.userId();
        validateRefreshToken(refreshToken, userId);
        MemberInfo findMemberInfo = getUserInfo(userId);
        Token issueToken = jwtProvider.issueToken(userId);
        updateRefreshToken(issueToken.refreshToken(), findMemberInfo);
        return UserLoginResponse.of(issueToken);
    }

    private Token generateTokens(final Long userId) {
        Token issuedToken = jwtProvider.issueToken(userId);
        jwtProvider.issueToken(userId);
        MemberInfo findMemberInfo = getUserInfo(userId);
        updateRefreshToken(issuedToken.refreshToken(), findMemberInfo);
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

    private Member loadOrCreateUser(Platform platform, SocialInfoDto socialInfo){
        boolean isRegistered = memberRepository.existsByPlatformAndSerialId(
                platform,
                socialInfo.serialId());
        if (!isRegistered){
            Member newMember = createUser(
                    socialInfo.name(),
                    socialInfo.email(),
                    socialInfo.serialId(),
                    platform);
            saveMember(newMember);
        }
        return getUser(platform, socialInfo.serialId());
    }

    private Member getUser(
            final Platform platform,
            final String serialId) {
        return memberRepository.findByPlatformAndSerialId(platform, serialId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    private MemberInfo getUserInfo(final Long memberId) {
        return memberInfoRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_INFO_NOT_FOUND));
    }

    private String getRefreshToken(final Long userId) {
        return memberInfoRepository.findByMemberId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND))
                .getRefreshToken();
    }

    private void saveMember(final Member member) {
        memberRepository.save(member);
        MemberInfo memberInfo = createMemberInfo(member, null);
        memberInfoRepository.save(memberInfo);
    }

    private void updateRefreshToken(
            final String refreshToken,
            final MemberInfo memberInfo) {
        memberInfo.updateRefreshToken(refreshToken);
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
