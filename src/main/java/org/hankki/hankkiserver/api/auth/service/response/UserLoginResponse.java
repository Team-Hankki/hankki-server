package org.hankki.hankkiserver.api.auth.service.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.hankki.hankkiserver.auth.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record UserLoginResponse(
        String accessToken,
        String refreshToken,
        boolean isRegisterd

) {
    public static UserLoginResponse of(Token token, boolean isRegisterd) {
        return UserLoginResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                . isRegisterd(isRegisterd)
                .build();
    }

    public static UserLoginResponse of(Token token) {
        return UserLoginResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
