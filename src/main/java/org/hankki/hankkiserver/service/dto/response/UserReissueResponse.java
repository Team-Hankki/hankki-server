package org.hankki.hankkiserver.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.hankki.hankkiserver.auth.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record UserReissueResponse(
        String accessToken,
        String refreshToken
) {
    public static UserReissueResponse of(Token token) {
        return UserReissueResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
