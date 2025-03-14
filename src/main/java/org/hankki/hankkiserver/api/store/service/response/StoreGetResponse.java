package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

import java.util.List;

public record StoreGetResponse(
        String name,
        String category,
        int heartCount,
        boolean isLiked,
        double latitude,
        double longitude,
        String categoryImageUrl,
        List<String> imageUrls,
        List<MenuResponse> menus
) {
    public static StoreGetResponse of(final Store store, final boolean isLiked, final List<String> imageUrls, final List<MenuResponse> menus) {
        return new StoreGetResponse(store.getName(),
                store.getCategory().getName(),
                store.getHeartCount(),
                isLiked,
                store.getPoint().getLatitude(),
                store.getPoint().getLongitude(),
                store.getCategory().getUrl(),
                imageUrls,
                menus);
    }
}
