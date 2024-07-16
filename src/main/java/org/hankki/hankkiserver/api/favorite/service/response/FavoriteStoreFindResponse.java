package org.hankki.hankkiserver.api.favorite.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteStoreFindResponse(
  Long id,
  String name,
  String imageType,
  String category,
  int lowestPrice,
  int heartCount
) {

  public static FavoriteStoreFindResponse of(Store store) {
    return new FavoriteStoreFindResponse(
        store.getId(),
        store.getName(),
        store.getImages().get(0).getImageUrl(),
        store.getCategory().getName(),
        store.getLowestPrice(),
        store.getHeartCount());
  }
}
