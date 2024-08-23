package org.hankki.hankkiserver.api.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUniversityPostRequest(
        Long universityId,
        @NotBlank @Size(min = 3, max = 20)
        String name,
        double longitude,
        double latitude
) {
}
