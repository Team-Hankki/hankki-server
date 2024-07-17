package org.hankki.hankkiserver.api.store.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;

import java.util.List;

public record StorePostRequest(
        String name,
        StoreCategory category,
        String address,
        double latitude,
        double longitude,
        long universityId,
        @Size(min = 1) @Valid
        List<MenuPostRequest> menus
) {
}
