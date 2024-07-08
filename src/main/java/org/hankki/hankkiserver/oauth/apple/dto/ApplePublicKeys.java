package org.hankki.hankkiserver.oauth.apple.dto;

import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.exception.UnauthorizedException;

import java.util.List;


public record ApplePublicKeys(
        List<ApplePublicKey> applePublicKeys
) {
    public ApplePublicKey getMatchedPublicKey(String kid, String alg) {
        return this.applePublicKeys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.INVALID_APPLE_IDENTITY_TOKEN));
    }
}
