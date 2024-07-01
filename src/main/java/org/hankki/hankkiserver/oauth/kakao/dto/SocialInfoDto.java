package org.hankki.hankkiserver.oauth.kakao.dto;

public record SocialInfoDto(
        String serialId,
        String name,
        String email) {
    public static SocialInfoDto of(String serialId, String name, String email) {
        return new SocialInfoDto(serialId, name, email);
    }
}
