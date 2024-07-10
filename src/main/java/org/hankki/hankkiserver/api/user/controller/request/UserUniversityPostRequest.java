package org.hankki.hankkiserver.api.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUniversityPostRequest(long universityId, @NotBlank @Size(max = 5) String name, double longitude, double latitude) {
}
