package org.hankki.hankkiserver.api.store.service.response;

public record CreateHeartResponse(
        Long storeId,
        boolean isHearted
) {
    public static CreateHeartResponse of(Long storeId, boolean isHearted) {
        return new CreateHeartResponse(storeId, isHearted);
    }
}
