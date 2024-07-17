package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

import java.util.List;

public record UserStoresReportedGetResponse(
        List<UserStoreReportedResponse> stores
) {
  public static UserStoresReportedGetResponse of(List<Store> stores) {
    return new UserStoresReportedGetResponse(stores.stream().map(UserStoreReportedResponse::of).toList());
  }
}
