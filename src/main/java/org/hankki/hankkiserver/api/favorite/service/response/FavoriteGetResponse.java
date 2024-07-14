package org.hankki.hankkiserver.api.favorite.service.response;

import static org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil.getDetail;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteGetResponse(
    String title,
    List<String> details,
    List<FavoriteStoreResponse> stores
) {

  public static FavoriteGetResponse of(final Favorite favorite, final List<Store> stores) {
    return new FavoriteGetResponse(
        favorite.getName(),
        getDetail(favorite.getDetail()),
        stores.stream().map(FavoriteStoreResponse::of).toList());
  }
}
