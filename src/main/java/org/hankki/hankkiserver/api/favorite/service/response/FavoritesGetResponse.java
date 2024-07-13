package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record FavoritesGetResponse(
    List<FavoriteResponse> favorites
) {
}
