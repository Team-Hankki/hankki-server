package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.api.store.parameter.SortOption;

public record SortOptionResponse(String name, String tag) {
    public static SortOptionResponse of(SortOption sortOption) {
        return new SortOptionResponse(sortOption.getName(), sortOption.toString());
    }
}
