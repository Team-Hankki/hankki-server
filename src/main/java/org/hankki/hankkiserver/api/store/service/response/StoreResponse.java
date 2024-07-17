package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record StoreResponse (long id,
                             String imageUrl,
                             String category,
                             String name,
                             int lowestPrice,
                             int heartCount) {

    public static StoreResponse of(final Store store) {
        return new StoreResponse(store.getId(), store.getImages().get(0).getImageUrl(), store.getCategory().getName(), store.getName(), store.getLowestPrice(), store.getHeartCount());
    }
}
