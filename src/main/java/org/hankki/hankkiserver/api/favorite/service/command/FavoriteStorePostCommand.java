package org.hankki.hankkiserver.api.favorite.service.command;

import org.hankki.hankkiserver.auth.UserId;
import org.springframework.web.bind.annotation.PathVariable;

public record FavoriteStorePostCommand(
    Long userId,
    Long favoriteId,
    Long storeId
) {

  public static FavoriteStorePostCommand of(Long userId, Long favoriteId, Long storeId) {
    return new FavoriteStorePostCommand(userId, favoriteId, storeId);
  }
}
