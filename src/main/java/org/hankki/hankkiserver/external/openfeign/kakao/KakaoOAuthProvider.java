package org.hankki.hankkiserver.external.openfeign.kakao;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.hankki.hankkiserver.external.openfeign.kakao.dto.KakaoUnlinkRequest;
import org.hankki.hankkiserver.external.openfeign.kakao.dto.KakaoUserInfo;
import org.springframework.stereotype.Component;

import static org.hankki.hankkiserver.external.openfeign.kakao.KakaoAccessToken.createKakaoAccessToken;

@Component
@RequiredArgsConstructor
public class KakaoOAuthProvider {

    private final KakaoFeignClient kakaoFeignClient;

    private static final String GRANT_TYPE = "KakaoAK ";

    public SocialInfoDto getKakaoUserInfo(final String providerToken) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(providerToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getUserInfo(accessTokenWithTokenType);
        return SocialInfoDto.of(
                kakaoUserInfo.id().toString(),
                kakaoUserInfo.kakaoAccount().kakaoUserProfile().nickname(),
                kakaoUserInfo.kakaoAccount().email());
    }

    public void unlinkKakaoServer(final String adminKey, KakaoUnlinkRequest kakaoUnlinkRequest) {
        kakaoFeignClient.unlinkKakaoServer(GRANT_TYPE + adminKey, kakaoUnlinkRequest.targetIdType(), kakaoUnlinkRequest.targetId());
    }
}
