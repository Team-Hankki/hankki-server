package org.hankki.hankkiserver.api.store.service.command;

public record StoreValidationCommand (
        long universityId,
        double latitude,
        double longitude,
        String name
) {
    public static StoreValidationCommand of(long universityId, double latitude, double longitude, String name) {
        return new StoreValidationCommand(universityId, latitude, longitude, name);
    }
}
