package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreImage;

public record UserHeartedStoreViewResponse(
        Long id,
        String name,
        String imageUrl,
        String category,
        int lowestPrice,
        int heartCount
) {
    public static UserHeartedStoreViewResponse of(Store store) {
        return new UserHeartedStoreViewResponse(
                store.getId(),
                store.getName(),
                getStoreImageUrl(store),
                store.getCategory().getName(),
                store.getLowestPrice(),
                store.getHeartCount()
        );
    }

    private static String getStoreImageUrl(Store store) {
        return store.getImages().stream()
                .findFirst()
                .map(StoreImage::getImageUrl)
                .orElse(null);
    }
}
