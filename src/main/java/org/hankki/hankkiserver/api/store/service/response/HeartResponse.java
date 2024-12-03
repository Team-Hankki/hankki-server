package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record HeartResponse(
        Long storeId,
        int count,
        boolean isHearted
) {
    public static HeartResponse of(final Store store) {
        return new HeartResponse(store.getId(), store.getHeartCount(), true);
    }
}
