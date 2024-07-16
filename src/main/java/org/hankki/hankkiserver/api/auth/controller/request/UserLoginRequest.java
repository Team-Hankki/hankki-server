package org.hankki.hankkiserver.api.auth.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @Nullable
        String name,
        @NotBlank
        String platform
) {
}
