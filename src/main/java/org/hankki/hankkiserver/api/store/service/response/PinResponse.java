package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.store.model.Store;

public record PinResponse(
        double latitude,
        double longitude,
        long id,
        String name
) {
    public static PinResponse of(final Store store) {
        return new PinResponse(store.getPoint().getLatitude(), store.getPoint().getLongitude(), store.getId(), store.getName());
    }
}
