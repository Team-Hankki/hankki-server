package org.hankki.hankkiserver.api.user.service.response;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record UserFavoritesGetResponse(
    List<UserFavoriteResponse> favorites
) {

  public static UserFavoritesGetResponse of(List<Favorite> favorites) {
    return new UserFavoritesGetResponse(favorites.stream().map(UserFavoriteResponse::of).toList());
  }
}