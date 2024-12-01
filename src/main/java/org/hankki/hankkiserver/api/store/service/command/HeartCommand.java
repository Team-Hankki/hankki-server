package org.hankki.hankkiserver.api.store.service.command;

public record HeartCommand(
        Long userId,
        Long storeId
) {
    public static HeartCommand of(final Long userId, final Long storeId) {
        return new HeartCommand(userId, storeId);
    }
}
