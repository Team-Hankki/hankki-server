package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

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
                store.getImages().get(0).getImageUrl(),
                store.getCategory().getName(),
                store.getLowestPrice(),
                store.getHeartCount()
        );
    }
}
