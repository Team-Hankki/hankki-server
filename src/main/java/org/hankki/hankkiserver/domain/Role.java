package org.hankki.hankkiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
    ADMIN("admin"),
    PARTICIPANTS("participants");

    private final String value;
}
