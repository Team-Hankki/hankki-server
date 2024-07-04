package org.hankki.hankkiserver.oauth.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleTokenRequest(
        String code,
        String clientId,
        String clientSecret,
        String grantType
) {
    private static final String GRANT_TYPE = "authorization_code";

    public static AppleTokenRequest of(
            final String code,
            final String clientId,
            final String clientSecret) {
        return new AppleTokenRequest(code, clientId, clientSecret, GRANT_TYPE);
    }
}
