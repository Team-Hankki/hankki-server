package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.List;

public record FavoriteWithStatusGetResponse(
    List<FavoriteResponse> favorites
) {
}
