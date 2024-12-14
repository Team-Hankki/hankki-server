package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoriteIsOwnerGetCommand(
    long userId,
    long favoriteId
) {
  public static FavoriteIsOwnerGetCommand of(final long userId, final long favoriteId) {
    return new FavoriteIsOwnerGetCommand(userId, favoriteId);
  }
}
