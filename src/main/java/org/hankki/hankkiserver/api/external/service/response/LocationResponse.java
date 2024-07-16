package org.hankki.hankkiserver.api.external.service.response;

import org.hankki.hankkiserver.external.openfeign.naver.dto.NaverLocationInfo;

public record LocationResponse(double latitude, double longitude, String name, String address) {
    private static final int RATIO = 10000000;
    public static LocationResponse of(NaverLocationInfo location) {
        return new LocationResponse(location.mapy()/RATIO, location.mapx()/RATIO, trim(location.title()), location.address());
    }

    private static String trim(String string) {
        return string.replaceAll("<[^>]*>", "");
    }
}
