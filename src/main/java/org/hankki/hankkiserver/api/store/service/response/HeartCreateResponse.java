package org.hankki.hankkiserver.api.store.service.response;

public record HeartCreateResponse(
        Long storeId,
        boolean isHearted,
        int count
) {
    public static HeartCreateResponse of(final Long storeId, final boolean isHearted, final int count) {
        return new HeartCreateResponse(storeId, isHearted, count);
    }
}
