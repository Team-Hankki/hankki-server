package org.hankki.hankkiserver.external.openfeign.oauth.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleTokenResponse(
        String accessToken,
        String tokenType,
        String expiresIn,
        String refreshToken,
        String idToken
        ) {
}

