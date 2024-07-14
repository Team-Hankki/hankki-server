package org.hankki.hankkiserver.api.store.controller.request;

public record StoreDuplicateValidationRequest(
        Long id,
        Double latitude,
        Double longitude
) {
}
