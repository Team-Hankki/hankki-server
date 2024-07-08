package org.hankki.hankkiserver.oauth.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleTokenResponse(
        String accessToken,
        String expireIn,
        String idToken,
        String refreshToken,
        String tokenType) {

    public static AppleTokenResponse of(
            final String accessToken,
            final String expireIn,
            final String idToken,
            final String refreshToken,
            final String tokenType) {
        return new AppleTokenResponse(accessToken, expireIn, idToken, refreshToken, tokenType);
    }
}

