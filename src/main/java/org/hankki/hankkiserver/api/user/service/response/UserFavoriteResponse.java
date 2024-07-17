package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.model.FavoriteImage;

public record UserFavoriteResponse(
  Long id,
  String title,
  FavoriteImage imageType
) {

  public static UserFavoriteResponse of(Favorite favorite) {
    return new UserFavoriteResponse(favorite.getId(), favorite.getName(), favorite.getImageType());
  }
}
