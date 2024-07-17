package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoritesGetCommand(
    Long userId,
    Long favoriteId
) {
  public static FavoritesGetCommand of(final Long userId, final Long favoriteId) {
    return new FavoritesGetCommand(userId, favoriteId);
  }
}