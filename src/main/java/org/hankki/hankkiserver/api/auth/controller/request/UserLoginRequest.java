package org.hankki.hankkiserver.api.auth.controller.request;

import jakarta.annotation.Nullable;

public record UserLoginRequest(
        @Nullable
        String name,
        String platform
) {
}
