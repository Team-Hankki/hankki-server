package org.hankki.hankkiserver.api.user.service.response;

import java.util.List;
import org.hankki.hankkiserver.domain.store.model.Store;

public record UserStoresReportedGetResponse(
    List<UserStoreReportedResponse> stores
) {
  public static UserStoresReportedGetResponse of(List<Store> stores) {
    return new UserStoresReportedGetResponse(stores.stream().map(UserStoreReportedResponse::of).toList());
  }
}
