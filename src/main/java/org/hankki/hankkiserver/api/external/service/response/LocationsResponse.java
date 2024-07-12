package org.hankki.hankkiserver.api.external.service.response;

import java.util.List;

public record LocationsResponse(
        List<LocationResponse> locations
) {
    public static LocationsResponse of(List<LocationResponse> locations) {
        return new LocationsResponse(locations);
    }
}
