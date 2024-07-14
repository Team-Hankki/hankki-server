package org.hankki.hankkiserver.api.store.controller.request;

public record StoreDuplicateValidationRequest(
        Double latitude,
        Double longitude
) {
}
