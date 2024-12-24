package org.hankki.hankkiserver.api.favorite.service.response;

import static org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil.transformDetail;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteSharedGetResponse(
    String title,
    List<String> details,
    String nickname,
    List<FavoriteStoreResponse> stores
) {
  public static FavoriteSharedGetResponse of(final Favorite favorite, final String nickname, final List<Store> stores) {
    return new FavoriteSharedGetResponse(
        favorite.getName(),
        transformDetail(favorite.getDetail()),
        nickname,
        stores.stream().map(FavoriteStoreResponse::of).toList());
  }
}
