package org.hankki.hankkiserver.api.store.service.command;

import org.hankki.hankkiserver.api.store.controller.request.StoreCreateRequest;

public record UniversityStorePostCommand(
        Long storeId,
        Long universityId
) {
    public static UniversityStorePostCommand of(StoreCreateRequest request) {
        return new UniversityStorePostCommand(request.storeId(), request.universityId());
    }
}
