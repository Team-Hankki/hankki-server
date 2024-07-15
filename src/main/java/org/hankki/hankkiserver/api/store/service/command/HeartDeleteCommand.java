package org.hankki.hankkiserver.api.store.service.command;

public record HeartDeleteCommand(
        Long userId,
        Long storeId
) {
    public static HeartDeleteCommand of(final Long userId, final Long storeId) {
        return new HeartDeleteCommand(userId, storeId);
    }
}

