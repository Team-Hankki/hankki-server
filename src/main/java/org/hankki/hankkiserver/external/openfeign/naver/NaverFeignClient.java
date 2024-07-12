package org.hankki.hankkiserver.external.openfeign.naver;

import org.hankki.hankkiserver.external.openfeign.naver.dto.NaverLocationsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "https://openapi.naver.com")
public interface NaverFeignClient {
    @GetMapping("/v1/search/local.json")
    NaverLocationsDto getLocationInfo(
        @RequestHeader("X-Naver-Client-Id") String clientId,
        @RequestHeader("X-Naver-Client-Secret") String clientSecret,
        @RequestParam("query") String query,
        @RequestParam("display") int display
    );
}
