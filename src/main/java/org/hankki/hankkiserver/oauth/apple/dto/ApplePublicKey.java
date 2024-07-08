package org.hankki.hankkiserver.oauth.apple.dto;

import java.util.Optional;

public record ApplePublicKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e) {
}
