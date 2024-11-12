package org.hankki.hankkiserver.api.university.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.university.controller.request.UniversitiesPostRequest;
import org.hankki.hankkiserver.api.university.service.UniversityCommandService;
import org.hankki.hankkiserver.api.university.service.UniversityQueryService;
import org.hankki.hankkiserver.api.university.service.command.UniversitiesPostCommand;
import org.hankki.hankkiserver.api.university.service.response.UniversitiesResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityQueryService universityQueryService;
    private final UniversityCommandService universityCommandService;

    @GetMapping("/universities")
    public HankkiResponse<UniversitiesResponse> getUniversities() {
        return HankkiResponse.success(CommonSuccessCode.OK, universityQueryService.findAllByOrderByName());
    }

    @PostMapping("/universities")
    public HankkiResponse<Void> createUniversities(@RequestBody UniversitiesPostRequest universitiesPostRequest) {
        universityCommandService.createUniversities(UniversitiesPostCommand.of(universitiesPostRequest));
        return HankkiResponse.success(CommonSuccessCode.CREATED);
    }
}
