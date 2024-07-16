package org.hankki.hankkiserver.api.store.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MenuPostRequest(
        @NotBlank @Size(max = 30)
        String name,
        @Max(8000)
        int price
) {
}
