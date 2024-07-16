package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.heart.model.Heart;

import java.util.List;

public record UserHeartedStoreListResponse(
        List<UserHeartedStoreViewResponse> stores
) {
    public static UserHeartedStoreListResponse of(List<Heart> hearts) {
        return new UserHeartedStoreListResponse(hearts.stream()
                .map(heart -> UserHeartedStoreViewResponse.of(heart.getStore()))
                .toList());
    }
}
