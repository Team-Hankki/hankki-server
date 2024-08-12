package org.hankki.hankkiserver.api.universitystore.controller.request;

public record UniviersityStoreCreateRequest(
        Long storeId,
        Long universityId
) {
}
