package org.hankki.hankkiserver.api.auth.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @Nullable
        String name,
        @NotNull
        String platform
) {
}
