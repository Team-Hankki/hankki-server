package org.hankki.hankkiserver.oauth.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleRevokeRequest(
        String token,
        String clientId,
        String clientSecret,
        String tokenTypeHint
) {

    private static final String TOKEN_TYPE_HINT = "refresh_token";

    public static AppleRevokeRequest of(
            final String token,
            final String clientId,
            final String clientSecret) {
        return new AppleRevokeRequest(token, clientId, clientSecret, TOKEN_TYPE_HINT);
    }
}
