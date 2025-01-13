package org.hankki.hankkiserver.api.auth.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthProviderFactory {

    private final Map<String, OAuthProvider> oAuthProviders;

    public OAuthProvider findProvider(final Platform platform) {
        return oAuthProviders.get(platform.getBeanName());
    }
}
