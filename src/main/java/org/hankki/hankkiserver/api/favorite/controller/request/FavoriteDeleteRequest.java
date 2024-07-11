package org.hankki.hankkiserver.api.favorite.controller.request;

import java.util.List;

public record FavoriteDeleteRequest(
    List<Long> favoriteIds
) {
}
