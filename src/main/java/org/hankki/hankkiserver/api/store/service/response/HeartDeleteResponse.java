package org.hankki.hankkiserver.api.store.service.response;

public record HeartDeleteResponse(
        Long storeId,
        boolean isHearted
) {
    public static HeartDeleteResponse of(Long storeId, boolean isHearted) {
        return new HeartDeleteResponse(storeId, isHearted);
    }
}
