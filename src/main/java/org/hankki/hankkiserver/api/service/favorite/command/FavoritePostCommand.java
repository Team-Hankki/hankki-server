package org.hankki.hankkiserver.api.service.favorite.command;

import java.util.List;
import org.hankki.hankkiserver.api.controller.favorite.request.FavoritePostRequest;

public record FavoritePostCommand(
    long memberId,
    String title,
    List<String> details
) {

  public static FavoritePostCommand of(final long memberId, final FavoritePostRequest favoritePostRequest) {

    return new FavoritePostCommand(memberId, favoritePostRequest.title(), favoritePostRequest.details());

  }
}
