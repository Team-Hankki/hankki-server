package org.hankki.hankkiserver.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {

    ACTIVE("ACTIVE"), INACTICE("INACTIVE");

    private final String value;

}
