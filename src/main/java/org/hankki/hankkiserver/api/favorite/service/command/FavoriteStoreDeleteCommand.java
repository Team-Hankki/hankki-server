package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoriteStoreDeleteCommand(
  Long userId,
  Long favoriteId,
  Long storeId
) {

  public static FavoriteStoreDeleteCommand of(Long userId, Long favoriteId, Long storeId) {
    return new FavoriteStoreDeleteCommand(userId, favoriteId, storeId);
  }
}
