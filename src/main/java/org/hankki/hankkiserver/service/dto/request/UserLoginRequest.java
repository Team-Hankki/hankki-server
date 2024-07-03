package org.hankki.hankkiserver.service.dto.request;

public record UserLoginRequest(
        String name,
        String platform
) {
}
