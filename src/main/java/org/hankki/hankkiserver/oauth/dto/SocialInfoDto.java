package org.hankki.hankkiserver.oauth.dto;

public record SocialInfoDto(
        String serialId,
        String name,
        String email) {
    public static SocialInfoDto of(
            final String serialId,
            final String name,
            final String email) {
        return new SocialInfoDto(serialId, name, email);
    }
}
