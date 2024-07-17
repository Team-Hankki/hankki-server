package org.hankki.hankkiserver.api.store.service.response;

import java.util.List;

public record StoresResponse(List<StoreResponse> stores) {
    public static StoresResponse of(final List<StoreResponse> response) {
        return new StoresResponse(response);
    }
}
