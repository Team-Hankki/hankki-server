package org.hankki.hankkiserver.api.store.service.command;

public record StoreDeleteCommand(
        Long userId,
        Long storeId
) {
    public static StoreDeleteCommand of(final Long userId, final Long storeId) {
        return new StoreDeleteCommand(userId, storeId);
    }
}

