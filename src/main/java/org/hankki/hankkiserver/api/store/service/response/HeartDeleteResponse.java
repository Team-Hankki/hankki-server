package org.hankki.hankkiserver.api.store.service.response;

public record HeartDeleteResponse(
        Long storeId,
        boolean isHearted,
        int count
) {
    public static HeartDeleteResponse of(final Long storeId, final boolean isHearted, final int count) {
        return new HeartDeleteResponse(storeId, isHearted, count);
    }
}
