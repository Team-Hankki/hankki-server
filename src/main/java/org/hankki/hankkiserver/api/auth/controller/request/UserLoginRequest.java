package org.hankki.hankkiserver.api.auth.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.hankki.hankkiserver.domain.user.model.Platform;

public record UserLoginRequest(
        @Nullable
        String name,
        @NotNull
        Platform platform
) {
}
