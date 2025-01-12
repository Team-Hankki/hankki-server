package org.hankki.hankkiserver.external.openfeign.login.dto;

public record SocialInfoDto(
        String serialId,
        String name,
        String email)
{
    private static final String DEFAULT_NAME = "한끼";

    public static SocialInfoDto of(final String serialId, String name, final String email) {
        if (name.isBlank()) {
            name = DEFAULT_NAME;
        }
        return new SocialInfoDto(serialId, name, email);
    }
}
