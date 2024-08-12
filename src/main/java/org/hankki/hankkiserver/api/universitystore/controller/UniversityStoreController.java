package org.hankki.hankkiserver.api.universitystore.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.universitystore.controller.request.UniviersityStoreCreateRequest;
import org.hankki.hankkiserver.api.universitystore.service.UniversityStoreCommandService;
import org.hankki.hankkiserver.api.universitystore.service.command.UniversityStorePostCommand;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UniversityStoreController {

    private final UniversityStoreCommandService universitystoreCommandService;

    @PostMapping("/university-stores")
    public HankkiResponse<Void> createStore(@UserId final Long userId,
                                            @RequestBody final UniviersityStoreCreateRequest request) {
        universitystoreCommandService.createUniversityStore(UniversityStorePostCommand.of(userId, request.storeId(), request.universityId()));
        return HankkiResponse.success(CommonSuccessCode.CREATED);
    }
}
