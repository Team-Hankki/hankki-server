package org.hankki.hankkiserver.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Platform {

    KAKAO("KAKAO"), APPLE("APPLE");

    private final String value;

}
