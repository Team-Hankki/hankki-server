package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.StoreCategory;

public record CategoryResponse (String name, String tag, String imageUrl) {
    public static CategoryResponse of(StoreCategory storeCategory) {
        return new CategoryResponse(storeCategory.getName(), storeCategory.toString(), storeCategory.getUrl());
    }
}
