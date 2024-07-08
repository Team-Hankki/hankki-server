package org.hankki.hankkiserver.external.openfeign.kakao;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.hankki.hankkiserver.external.openfeign.kakao.dto.KakaoUserInfo;
import org.springframework.stereotype.Component;

import static org.hankki.hankkiserver.external.openfeign.kakao.KakaoAccessToken.createKakaoAccessToken;

@Component
@RequiredArgsConstructor
public class KakaoOAuthProvider {

    private final KakaoFeignClient kakaoFeignClient;

    public SocialInfoDto getKakaoUserInfo(final String providerToken) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(providerToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getUserInfo(accessTokenWithTokenType);
        return SocialInfoDto.of(
                kakaoUserInfo.id().toString(),
                kakaoUserInfo.kakaoAccount().kakaoUserProfile().nickname(),
                kakaoUserInfo.kakaoAccount().email());
    }
}
