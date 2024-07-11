package org.hankki.hankkiserver.external.openfeign.kakao.dto;

public record KakaoUnlinkRequest(
        String targetIdType,
        Long targetId
) {
    private static final String TARGET_ID_TYPE = "user_id";

    public static KakaoUnlinkRequest of(final String targetId) {
        return new KakaoUnlinkRequest(TARGET_ID_TYPE, Long.parseLong(targetId));
    }
}
