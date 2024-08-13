package org.hankki.hankkiserver.api.store.service.command;

public record StoreValidationCommand (
        long universityId,
        double latitude,
        double longitude
) {
    public static StoreValidationCommand of(long universityId, double latitude, double longitude) {
        return new StoreValidationCommand(universityId, latitude, longitude);
    }
}
