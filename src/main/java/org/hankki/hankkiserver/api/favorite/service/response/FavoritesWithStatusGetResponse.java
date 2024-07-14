package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.LinkedHashMap;
import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record FavoritesWithStatusGetResponse(
    List<FavoriteWithStatusResponse> favorites
) {
  public static FavoritesWithStatusGetResponse of(LinkedHashMap<Favorite, Boolean> favoriteWithStatusMap) {
    return new FavoritesWithStatusGetResponse(favoriteWithStatusMap.entrySet().stream()
        .map(entry -> FavoriteWithStatusResponse.of(entry.getKey(), entry.getValue()))
        .toList());
  }
}
