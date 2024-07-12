package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record UserFavoriteResponse(
  Long id,
  String title,
  String imageUrl
) {

  public static UserFavoriteResponse of(Favorite favorite) {
    return new UserFavoriteResponse(favorite.getId(), favorite.getName(), favorite.getImageUrl());
  }
}
