package org.hankki.hankkiserver.api.favorite.service.command;

import java.util.List;

public record FavoriteSharedPostCommand (
    long userId,
    long sharedFavoriteId,
    String title,
    List<String> details
) {
  public static FavoriteSharedPostCommand of(final long userId, final long sharedFavoriteId, final String title, final List<String> details) {
    return new FavoriteSharedPostCommand(userId, sharedFavoriteId, title, details);
  }
}
