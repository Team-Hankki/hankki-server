package org.hankki.hankkiserver.api.universitystore.service.command;

public record UniversityStorePostCommand(
        Long storeId,
        Long universityId
) {
    public static UniversityStorePostCommand of(final Long storeId, final Long universityId) {
        return new UniversityStorePostCommand(storeId, universityId);
    }
}
