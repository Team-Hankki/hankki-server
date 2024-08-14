package org.hankki.hankkiserver.api.favorite.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record FavoriteStoreResponse(
  Long id,
  String name,
  String imageUrl,
  String category,
  int lowestPrice,
  int heartCount
) {
  public static FavoriteStoreResponse of(Store store) {
    return new FavoriteStoreResponse(
        store.getId(),
        store.getName(),
        store.getImageUrlOrElseNull(),
        store.getCategory().getName(),
        store.getLowestPrice(),
        store.getHeartCount());
  }
}