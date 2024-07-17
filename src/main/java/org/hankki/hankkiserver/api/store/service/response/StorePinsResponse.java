package org.hankki.hankkiserver.api.store.service.response;

import java.util.List;

public record StorePinsResponse(List<PinResponse> pins) {
    public static StorePinsResponse of (final List<PinResponse> pins) {
        return new StorePinsResponse(pins);
    }
}
