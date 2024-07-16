package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record HeartDeleteResponse(
        Long storeId,
        int count,
        boolean isHearted
) {
    public static HeartDeleteResponse of(final Store store) {
        return new HeartDeleteResponse(store.getId(), store.getHeartCount(), false);
    }
}
