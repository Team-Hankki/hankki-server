package org.hankki.hankkiserver.api.favorite.service.response;

import org.hankki.hankkiserver.domain.favorite.model.Favorite;

import java.util.List;
import org.hankki.hankkiserver.domain.store.model.Store;

import static org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil.transformDetail;

public record FavoriteGetResponse(
    String title,
    List<String> details,
    List<FavoriteStoreResponse> stores
) {
  public static FavoriteGetResponse of(final Favorite favorite, final List<Store> stores) {
    return new FavoriteGetResponse(
        favorite.getName(),
        transformDetail(favorite.getDetail()),
        stores.stream().map(FavoriteStoreResponse::of).toList());
  }
}
