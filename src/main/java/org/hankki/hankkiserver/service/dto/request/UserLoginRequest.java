package org.hankki.hankkiserver.service.dto.request;

import jakarta.annotation.Nullable;

public record UserLoginRequest(
        @Nullable
        String name,
        String platform
) {
}
