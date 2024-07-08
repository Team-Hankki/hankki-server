package org.hankki.hankkiserver.external.openfeign.apple.dto;

import java.util.Optional;

public record ApplePublicKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e) {
}
