package org.hankki.hankkiserver.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.ErrorMessage;
import org.hankki.hankkiserver.exception.InvalidValueException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Platform {

    KAKAO("KAKAO"),
    APPLE("APPLE");

    private final String loginPlatform;

    public static Platform getEnumPlatformFromStringPlatform(String loginPlatform) {
        return Arrays.stream(values())
                .filter(platform -> platform.loginPlatform.equals(loginPlatform))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorMessage.INVALID_PLATFORM_TYPE));
    }
}
