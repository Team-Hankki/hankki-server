package org.hankki.hankkiserver.api.store.service.response;

import java.util.List;

public record StorePageResponse(
        List<StoreResponse> stores,
        boolean hasNext,
        CustomCursor cursor) {

    public static StorePageResponse of(final List<StoreResponse> stores, final boolean hasNext, final CustomCursor cursor) {
        return new StorePageResponse(stores, hasNext, cursor);
    }
}
