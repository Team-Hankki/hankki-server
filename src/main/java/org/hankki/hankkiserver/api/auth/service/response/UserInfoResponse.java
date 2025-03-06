package org.hankki.hankkiserver.api.auth.service.response;

import org.hankki.hankkiserver.domain.user.model.Platform;

public record UserInfoResponse(
        Platform platform,
        String serialId,
        String name,
        String email
) {
    public static UserInfoResponse of(final Platform platform, final String serialId, final String name, final String email) {
        return new UserInfoResponse(platform, serialId, name, email);
    }
}
