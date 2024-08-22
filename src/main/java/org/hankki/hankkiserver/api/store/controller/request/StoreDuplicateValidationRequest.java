package org.hankki.hankkiserver.api.store.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreDuplicateValidationRequest(
        @NotNull
        long universityId,
        @NotNull
        double latitude,
        @NotNull
        double longitude,
        @NotBlank
        String storeName
) {
}
