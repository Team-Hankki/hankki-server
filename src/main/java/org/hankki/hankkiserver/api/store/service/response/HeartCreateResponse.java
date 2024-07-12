package org.hankki.hankkiserver.api.store.service.response;

public record HeartCreateResponse(
        Long storeId,
        boolean isHearted
) {
    public static HeartCreateResponse of(final Long storeId, final boolean isHearted) {
        return new HeartCreateResponse(storeId, isHearted);
    }
}
