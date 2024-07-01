package org.hankki.hankkiserver.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.exception.InvalidValueException;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Platform {

    KAKAO("kakao"),
    APPLE("apple");

    private final String loginPlatform;

    public static Platform getEnumPlatformFromStringPlatform(String loginPlatform) {
        return Arrays.stream(values())
                .filter(platform -> platform.loginPlatform.equals(loginPlatform))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorMessage.INVALID_PLATFORM_TYPE));
    }
}
