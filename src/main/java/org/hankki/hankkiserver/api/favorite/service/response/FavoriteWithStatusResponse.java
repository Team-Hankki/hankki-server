package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.List;
import org.hankki.hankkiserver.api.favorite.service.response.util.FavoriteResponseUtil;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.model.FavoriteImage;

public record FavoriteWithStatusResponse(
    Long id,
    String title,
    FavoriteImage imageType,
    List<String> details,
    boolean isAdded
) {
  public static FavoriteWithStatusResponse of(Favorite favorite, boolean isReported) {
    return new FavoriteWithStatusResponse(
        favorite.getId(),
        favorite.getName(),
        favorite.getImageType(),
        FavoriteResponseUtil.transformDetail(favorite.getDetail()),
        isReported);
  }
}