package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalService {

    private final OAuthProviderFactory oAuthProviderFactory;

    protected SocialInfoResponse getUserInfo(final String token, final Platform platform, final String name) {
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        return oAuthProvider.getUserInfo(token, name);
    }

    protected void revoke(final Platform platform, final String code, final String serialId) {
        OAuthProvider oAuthProvider = oAuthProviderFactory.findProvider(platform);
        oAuthProvider.requestRevoke(code, serialId);
    }
}
