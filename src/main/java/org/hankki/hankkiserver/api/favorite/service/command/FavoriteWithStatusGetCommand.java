package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoriteWithStatusGetCommand(
    Long userId,
    Long favoriteId
) {
  public static FavoriteWithStatusGetCommand of(final Long userId, final Long favoriteId) {
    return new FavoriteWithStatusGetCommand(userId, favoriteId);
  }
}