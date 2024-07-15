package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record StorePostResponse (
        long id,
        String name
) {
    public static StorePostResponse of(Store store) {
        return new StorePostResponse(store.getId(), store.getName());
    }
}
