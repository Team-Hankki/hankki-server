package org.hankki.hankkiserver.api.store.service.response;

public record HeartDeleteResponse(
        Long storeId,
        boolean isHearted
) {
    public static HeartDeleteResponse of(final Long storeId, final boolean isHearted) {
        return new HeartDeleteResponse(storeId, isHearted);
    }
}
