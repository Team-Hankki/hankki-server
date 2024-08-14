package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record UserStoreReportedResponse(
        Long id,
        String name,
        String category,
        String imageUrl,
        int lowestPrice,
        int heartCount
) {
  public static UserStoreReportedResponse of(Store store) {
    return new UserStoreReportedResponse(
        store.getId(),
        store.getName(),
        store.getCategory().getName(),
        store.getImageUrlOrElseNull(),
        store.getLowestPrice(),
        store.getHeartCount());
  }
}
