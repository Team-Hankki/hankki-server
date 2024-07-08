package org.hankki.hankkiserver.external.openfeign.apple.dto;

import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;

import java.util.List;


public record ApplePublicKeys(
        List<ApplePublicKey> applePublicKeys
) {
    public ApplePublicKey getMatchedPublicKey(String kid, String alg) {
        return this.applePublicKeys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_APPLE_IDENTITY_TOKEN));
    }
}
