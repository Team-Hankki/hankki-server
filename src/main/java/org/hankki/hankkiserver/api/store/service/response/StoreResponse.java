package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;

public record StoreResponse (
        long id,
        String imageUrl,
        String category,
        String name,
        int lowestPrice,
        int heartCount
) {
    public static StoreResponse of(final Store store) {
        return new StoreResponse(store.getId(),
                store.getImages().isEmpty() ? null : store.getImages().get(0).getImageUrl(),
                store.getCategory().getName(),
                store.getName(),
                store.getLowestPrice(),
                store.getHeartCount());
    }

    public static StoreResponse of(final UniversityStore universityStore) {
        return new StoreResponse(universityStore.getStore().getId(),
                universityStore.getStore().getImages().isEmpty() ? null : universityStore.getStore().getImages().get(0).getImageUrl(),
                universityStore.getStore().getCategory().getName(),
                universityStore.getStore().getName(),
                universityStore.getStore().getLowestPrice(),
                universityStore.getStore().getHeartCount());
    }
}
