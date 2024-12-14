package org.hankki.hankkiserver.api.favorite.service.command;

public record FavoriteOwnershipGetCommand(
    long userId,
    long favoriteId
) {
  public static FavoriteOwnershipGetCommand of(final long userId, final long favoriteId) {
    return new FavoriteOwnershipGetCommand(userId, favoriteId);
  }
}
