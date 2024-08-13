package org.hankki.hankkiserver.api.store.service.response;

public record StoreDuplicateValidationResponse(
        Long id,
        boolean isRegistered
) {
}
