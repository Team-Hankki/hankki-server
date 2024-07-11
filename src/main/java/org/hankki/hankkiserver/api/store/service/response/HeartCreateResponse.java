package org.hankki.hankkiserver.api.store.service.response;

public record HeartCreateResponse(
        Long storeId,
        boolean isHearted
) {
    public static HeartCreateResponse of(Long storeId, boolean isHearted) {
        return new HeartCreateResponse(storeId, isHearted);
    }
}
