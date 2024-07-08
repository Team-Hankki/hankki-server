package org.hankki.hankkiserver.api.favorite.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record FavoriteDeleteRequest(
    @NotEmpty
    List<@NotNull Long> favoriteIds
) {
}
