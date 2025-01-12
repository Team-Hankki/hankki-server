package org.hankki.hankkiserver.external.openfeign.oauth.kakao;

import static org.hankki.hankkiserver.external.openfeign.oauth.kakao.KakaoAccessToken.createKakaoAccessToken;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.external.openfeign.oauth.OAuthProvider;
import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoDto;
import org.hankki.hankkiserver.external.openfeign.oauth.kakao.dto.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOAuthProvider implements OAuthProvider {

    @Value("${oauth.kakao.key}")
    private String adminKey;
    private final KakaoFeignClient kakaoFeignClient;
    private static final String GRANT_TYPE = "KakaoAK ";
    private static final String TARGET_ID_TYPE = "user_id";

    @Override
    public SocialInfoDto getUserInfo(final String providerToken, final String name) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(providerToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getUserInfo(accessTokenWithTokenType);
        return SocialInfoDto.of(
                kakaoUserInfo.id().toString(),
                kakaoUserInfo.kakaoAccount().kakaoUserProfile().nickname(),
                kakaoUserInfo.kakaoAccount().email());
    }

    @Override
    public void requestRevoke(final String authorizationCode, final String serialId) {
        kakaoFeignClient.unlinkKakaoServer(GRANT_TYPE + adminKey, TARGET_ID_TYPE, Long.parseLong(serialId));
    }
}
