package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteFindResponse(
    String title,
    List<String> details,
    List<FavoriteStoreFindResponse> stores
) {

  public static FavoriteFindResponse of(Favorite favorite, List<Store> stores) {

    List<String> details = new ArrayList<>();
    if (favorite.getDetail() != null) {
      details = Arrays.asList(favorite.getDetail().split(" "));
    }

    return new FavoriteFindResponse(
        favorite.getName(),
        details,
        stores.stream().map(FavoriteStoreFindResponse::of).toList());
  }
}
