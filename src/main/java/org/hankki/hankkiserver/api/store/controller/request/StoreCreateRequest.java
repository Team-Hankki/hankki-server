package org.hankki.hankkiserver.api.store.controller.request;

public record StoreCreateRequest(
        Long storeId,
        Long universityId
) {
}
