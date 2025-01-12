package org.hankki.hankkiserver.external.openfeign.oauth.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAccessToken {

    private static final String TOKEN_TYPE = "Bearer ";
    private String accessToken;

    protected static KakaoAccessToken createKakaoAccessToken(String accessToken) {
        return new KakaoAccessToken(accessToken);
    }

    protected String getAccessTokenWithTokenType() {
        return TOKEN_TYPE + accessToken;
    }
}
