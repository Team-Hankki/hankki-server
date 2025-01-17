package org.hankki.hankkiserver.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Platform {

    KAKAO("KAKAO"),
    APPLE("APPLE");

    private static final String SUFFIX = "OAuthProvider";
    private final String loginPlatform;

    public String getBeanName() {
        return loginPlatform.toLowerCase() + SUFFIX;
    }
}
