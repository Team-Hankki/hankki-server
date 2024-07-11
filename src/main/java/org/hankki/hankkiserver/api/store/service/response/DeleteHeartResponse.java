package org.hankki.hankkiserver.api.store.service.response;

public record DeleteHeartResponse(
        Long storeId,
        boolean isHearted
) {
    public static DeleteHeartResponse of(Long storeId, boolean isHearted) {
        return new DeleteHeartResponse(storeId, isHearted);
    }
}
