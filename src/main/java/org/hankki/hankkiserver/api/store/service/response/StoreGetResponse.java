package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

import java.util.List;

public record StoreGetResponse(
        String name,
        String category,
        int heartCount,
        boolean isLiked,
        List<String> imageUrls,
        List<MenuResponse> menus
) {
    public static StoreGetResponse of(Store store, boolean isLiked, List<MenuResponse> menus) {
        return new StoreGetResponse(store.getName(),
                store.getCategory().getName(),
                store.getHeartCount(),
                isLiked,
                store.getImages().stream()
                        .map(storeImage -> storeImage.getImageUrl())
                        .toList(),
                menus);
    }
}
