package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;

public record FavoriteResponse(
    Long id,
    String title,
    String imageUrl,
    List<String> details,
    boolean isReported
) {
}