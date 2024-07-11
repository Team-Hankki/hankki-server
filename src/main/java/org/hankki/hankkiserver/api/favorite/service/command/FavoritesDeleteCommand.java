package org.hankki.hankkiserver.api.favorite.service.command;

import java.util.List;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;

public record FavoritesDeleteCommand(
    Long userId,
    List<Long> favoriteIds
) {

  public static FavoritesDeleteCommand of(final Long userId, final FavoriteDeleteRequest favoriteDeleteRequest) {
    return new FavoritesDeleteCommand(userId, favoriteDeleteRequest.favoriteIds());
  }
}
