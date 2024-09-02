package org.hankki.hankkiserver.api.store.service.command;

public record StoreDeleteCommand(Long storeId,
                                 Long userId) {
    public static StoreDeleteCommand of(Long storeId, Long userId) {
        return new StoreDeleteCommand(storeId, userId);
    }
}
