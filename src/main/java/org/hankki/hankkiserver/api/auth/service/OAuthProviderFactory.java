package org.hankki.hankkiserver.api.auth.service;

import static org.hankki.hankkiserver.domain.user.model.Platform.KAKAO;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.external.openfeign.login.OAuthProvider;
import org.hankki.hankkiserver.external.openfeign.login.apple.AppleOAuthProvider;
import org.hankki.hankkiserver.external.openfeign.login.kakao.KakaoOAuthProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthProviderFactory {

    private final KakaoOAuthProvider kakaoOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;

    public OAuthProvider findProvider(final Platform platform) {
        if (KAKAO == platform) {
            return kakaoOAuthProvider;
        }
        return appleOAuthProvider;
    }
}
