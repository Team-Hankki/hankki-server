package org.hankki.hankkiserver.api.universitystore.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.universitystore.controller.request.UniviersityStoreCreateRequest;
import org.hankki.hankkiserver.api.universitystore.service.UniversitystoreCommandService;
import org.hankki.hankkiserver.api.universitystore.service.command.UniversityStorePostCommand;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UniversityStoreController {

    private final UniversitystoreCommandService universitystoreCommandService;

    @PostMapping("/universitystores")
    public HankkiResponse<Void> createStore(@RequestBody final UniviersityStoreCreateRequest request) {
        universitystoreCommandService.createUniversityStore(UniversityStorePostCommand.of(request.storeId(), request.universityId()));
        return HankkiResponse.success(CommonSuccessCode.CREATED);
    }
}
