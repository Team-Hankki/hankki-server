package org.hankki.hankkiserver.api.favorite.service.command;

import java.util.List;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;

public record FavoritePostCommand(
    Long userId,
    String title,
    List<String> details
) {

  public static FavoritePostCommand of(final Long userId, final FavoritePostRequest favoritePostRequest) {

    return new FavoritePostCommand(userId, favoritePostRequest.title(), favoritePostRequest.details());

  }
}
