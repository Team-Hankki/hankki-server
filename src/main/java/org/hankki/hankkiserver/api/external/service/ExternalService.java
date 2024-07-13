package org.hankki.hankkiserver.api.external.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.external.service.response.LocationResponse;
import org.hankki.hankkiserver.api.external.service.response.LocationsResponse;
import org.hankki.hankkiserver.external.openfeign.naver.dto.NaverLocationsDto;
import org.hankki.hankkiserver.external.openfeign.naver.NaverFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalService {

    @Value("${naver.clientId}")
    private String clientId;
    @Value("${naver.clientSecret}")
    private String clientSecret;
    private static final int DEFAULT_SEARCH_SIZE = 5;

    private final NaverFeignClient naverFeignClient;

    public LocationsResponse getLocations(String query) {
        NaverLocationsDto locationsDto = naverFeignClient.getLocationInfo(clientId, clientSecret, query, DEFAULT_SEARCH_SIZE);
        return LocationsResponse.of(locationsDto.items().stream()
                .map(LocationResponse::of)
                .toList());
    }
}
