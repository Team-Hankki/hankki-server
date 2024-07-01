package org.hankki.hankkiserver.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.hankki.hankkiserver.auth.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record UserLoginResponse(
        String accessToken,
        String refreshToken

) {
    public static UserLoginResponse of(Token token) {
        return UserLoginResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
