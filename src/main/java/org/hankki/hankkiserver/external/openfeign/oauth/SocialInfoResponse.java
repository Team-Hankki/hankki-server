package org.hankki.hankkiserver.external.openfeign.oauth;

public record SocialInfoResponse(
        String serialId,
        String name,
        String email)
{
    private static final String DEFAULT_NAME = "한끼";

    public static SocialInfoResponse of(final String serialId, String name, final String email) {
        if (name == null || name.isBlank()) {
            name = DEFAULT_NAME;
        }
        return new SocialInfoResponse(serialId, name, email);
    }
}
