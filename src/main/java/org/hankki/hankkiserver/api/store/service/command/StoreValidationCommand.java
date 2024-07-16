package org.hankki.hankkiserver.api.store.service.command;

import org.hankki.hankkiserver.api.store.controller.request.StoreDuplicateValidationRequest;

public record StoreValidationCommand (
        long universityId,
        double latitude,
        double longitude
) {
    public static StoreValidationCommand of(StoreDuplicateValidationRequest request) {
        return new StoreValidationCommand(request.universityId(), request.latitude(), request.longitude());
    }
}
