package org.hankki.hankkiserver.external.openfeign.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleTokenResponse(
        String accessToken,
        String expiresIn,
        String idToken,
        String refreshToken,
        String tokenType) {

    public static AppleTokenResponse of(
            final String accessToken,
            final String expiresIn,
            final String idToken,
            final String refreshToken,
            final String tokenType) {
        return new AppleTokenResponse(accessToken, expiresIn, idToken, refreshToken, tokenType);
    }
}

