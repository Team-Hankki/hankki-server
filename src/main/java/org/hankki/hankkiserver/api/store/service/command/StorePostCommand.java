package org.hankki.hankkiserver.api.store.service.command;

public record StorePostCommand(
        Long userId,
        Long storeId
) {
    public static StorePostCommand of(Long userId, Long storeId) {
        return new StorePostCommand(userId, storeId);
    }
}
