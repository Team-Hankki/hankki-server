package org.hankki.hankkiserver.api.store.service.command;

public record StoreDeleteCommand(
        Long userId,
        Long storeId
) {
    public static StoreDeleteCommand of(Long userId, Long storeId) {
        return new StoreDeleteCommand(userId, storeId);
    }
}

