package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.List;
import org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record FavoriteWithStatusResponse(
    Long id,
    String title,
    String imageUrl,
    List<String> details,
    boolean isReported
) {
  public static FavoriteWithStatusResponse of(Favorite favorite, boolean isReported) {
    return new FavoriteWithStatusResponse(
        favorite.getId(),
        favorite.getName(),
        favorite.getImageUrl(),
        FavoriteResponseUtil.getDetail(favorite.getDetail()),
        isReported);
  }
}