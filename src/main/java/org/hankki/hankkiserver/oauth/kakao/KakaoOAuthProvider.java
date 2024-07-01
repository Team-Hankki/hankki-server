package org.hankki.hankkiserver.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.oauth.kakao.dto.KakaoUserInfo;
import org.hankki.hankkiserver.oauth.kakao.dto.SocialInfoDto;
import org.springframework.stereotype.Service;

import static org.hankki.hankkiserver.oauth.kakao.KakaoAccessToken.createKakaoAccessToken;

@Service
@RequiredArgsConstructor
public class KakaoOAuthProvider {

    private final KakaoFeignClient kakaoFeignClient;
    public SocialInfoDto getUserInfo(final String providerToken) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(providerToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getUserInfo(accessTokenWithTokenType);
        return SocialInfoDto.of(
                kakaoUserInfo.id().toString(),
                kakaoUserInfo.kakaoAccount().kakaoUserProfile().nickname(),
                kakaoUserInfo.kakaoAccount().email());
    }
}
