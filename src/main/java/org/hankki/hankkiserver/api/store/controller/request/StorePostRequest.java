package org.hankki.hankkiserver.api.store.controller.request;

import org.hankki.hankkiserver.domain.store.model.StoreCategory;

import java.util.List;

public record StorePostRequest(
        String name,
        StoreCategory category,
        String address,
        double latitude,
        double longitude,
        List<MenuPostRequest> menus
        ) {
}
