package org.hankki.hankkiserver.api.universitystore.service.command;

public record UniversityStorePostCommand(
        Long userId,
        Long storeId,
        Long universityId
) {
    public static UniversityStorePostCommand of(final Long userId, final Long storeId, final Long universityId) {
        return new UniversityStorePostCommand(userId, storeId, universityId);
    }
}
