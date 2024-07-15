package org.hankki.hankkiserver.api.favorite.service.response;

import static org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil.getDetail;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record FavoriteGetResponse(
    String title,
    List<String> details,
    List<FavoriteStoreResponse> stores
) {

  public static FavoriteGetResponse of(final Favorite favorite) {
    return new FavoriteGetResponse(
        favorite.getName(),
        getDetail(favorite.getDetail()),
        favorite.getFavoriteStores().stream().map(favoriteStore -> FavoriteStoreResponse.of(favoriteStore.getStore())).toList());
  }
}
