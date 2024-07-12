package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoriteGetCommand(
    Long userId,
    Long favoriteId
) {
  public static FavoriteGetCommand of(final Long userId, final Long favoriteId) {
    return new FavoriteGetCommand(userId, favoriteId);
  }
}