package org.hankki.hankkiserver.api.auth.controller.request;

import jakarta.annotation.Nullable;
import org.hankki.hankkiserver.domain.user.model.Platform;

public record UserLoginRequest(
        @Nullable
        String name,
        Platform platform
) {
}
