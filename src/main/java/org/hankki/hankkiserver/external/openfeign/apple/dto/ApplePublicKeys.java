package org.hankki.hankkiserver.external.openfeign.apple.dto;

import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;

import java.util.List;


public record ApplePublicKeys(
        List<ApplePublicKey> keys
) {
    public ApplePublicKey getMatchedPublicKey(String kid, String alg) {
        return keys.stream()
                .filter(applePublicKey -> applePublicKey.kid().equals(kid) && applePublicKey.alg().equals(alg))
                .findAny()
                .orElseThrow(() -> new UnauthorizedException(AuthErrorCode.INVALID_APPLE_IDENTITY_TOKEN));
    }
}
