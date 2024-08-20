package org.hankki.hankkiserver.api.store.controller.request;

public record StoreDuplicateValidationRequest(
        long universityId,
        double latitude,
        double longitude,
        String storeName
) {
}
