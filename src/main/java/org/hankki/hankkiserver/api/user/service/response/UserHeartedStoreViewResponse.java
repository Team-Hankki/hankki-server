package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.heart.model.Heart;

public record UserHeartedStoreViewResponse(
        Long id,
        String name,
        String imageUrl,
        String category,
        int lowestPrice,
        int heartCount
) {
    public static UserHeartedStoreViewResponse of(Heart heart) {
        return new UserHeartedStoreViewResponse(
                heart.getStore().getId(),
                heart.getStore().getName(),
                heart.getStore().getImage(),
                heart.getStore().getCategory().getName(),
                heart.getStore().getLowestPrice(),
                heart.getStore().getHeartCount()
        );
    }
}
