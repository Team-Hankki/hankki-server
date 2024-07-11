package org.hankki.hankkiserver.api.favorite.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;

public record FavoriteStoreFindResponse(
  Long id,
  String name,
  String imageUrl,
  String storeCategory,
  int lowestPrice
) {

  public static FavoriteStoreFindResponse of(Store store) {

    return new FavoriteStoreFindResponse(
        store.getId(),
        store.getName(),
        store.getImage(),
        store.getCategory().getName(),
        store.getLowestPrice());
  }
}
