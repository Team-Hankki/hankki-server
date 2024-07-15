package org.hankki.hankkiserver.api.store.service.command;

public record HeartPostCommand(
        Long userId,
        Long storeId
) {
    public static HeartPostCommand of(final Long userId, final Long storeId) {
        return new HeartPostCommand(userId, storeId);
    }
}
