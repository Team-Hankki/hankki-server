package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record StoreThumbnailResponse(
        long id,
        String name,
        String category,
        int lowestPrice,
        int heartCount
) {
    public static StoreThumbnailResponse of(final Store store) {
        return new StoreThumbnailResponse(
                store.getId(),
                store.getName(),
                store.getCategory().getName(),
                store.getLowestPrice(),
                store.getHeartCount()
        );
    }
}
