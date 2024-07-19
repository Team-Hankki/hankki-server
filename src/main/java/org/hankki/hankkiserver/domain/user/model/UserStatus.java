package org.hankki.hankkiserver.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
}
