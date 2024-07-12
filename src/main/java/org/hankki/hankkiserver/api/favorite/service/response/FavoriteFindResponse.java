package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteFindResponse(
    String title,
    List<String> details,
    List<FavoriteStoreFindResponse> stores
) {

  public static FavoriteFindResponse of(final Favorite favorite, final List<Store> stores) {
    return new FavoriteFindResponse(
        favorite.getName(),
        extractDetails(favorite),
        stores.stream().map(FavoriteStoreFindResponse::of).toList());
  }

  private static List<String> extractDetails(Favorite favorite) {
    if (favorite.getDetail() != null) {
      return Arrays.asList(favorite.getDetail().split(" "));
    }
    return new ArrayList<>();
  }
}
