package org.hankki.hankkiserver.api.external.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.external.service.ExternalService;
import org.hankki.hankkiserver.api.external.service.response.LocationsResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ExternalController {

    private final ExternalService externalService;

    @GetMapping("/locations")
    public HankkiResponse<LocationsResponse> getLocations(@RequestParam String query) {
        return HankkiResponse.success(CommonSuccessCode.OK, externalService.getLocations(query));
    }
}
