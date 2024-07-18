package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoritesWithStatusGetCommand(
  Long userId,
  Long storeId
) {
  public static FavoritesWithStatusGetCommand of(final Long userId, final Long storeId) {
    return new FavoritesWithStatusGetCommand(userId, storeId);
  }
}