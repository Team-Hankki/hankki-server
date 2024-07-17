package org.hankki.hankkiserver.api.favorite.service.command;

import org.hankki.hankkiserver.api.favorite.controller.request.FavoritesGetRequest;

public record FavoritesWithStatusGetCommand(
  Long userId,
  Long storeId
) {
  public static FavoritesWithStatusGetCommand of(final Long userId, final FavoritesGetRequest request) {
    return new FavoritesWithStatusGetCommand(userId, request.storeId());
  }
}
