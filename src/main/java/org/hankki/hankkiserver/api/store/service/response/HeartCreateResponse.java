package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record HeartCreateResponse(
        Long storeId,
        int count,
        boolean isHearted
) {
    public static HeartCreateResponse of(final Store store) {
        return new HeartCreateResponse(store.getId(), store.getHeartCount(), true);
    }
}
