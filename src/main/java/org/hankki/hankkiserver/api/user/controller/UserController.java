package org.hankki.hankkiserver.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.user.controller.request.UserUniversityPostRequest;
import org.hankki.hankkiserver.api.user.service.UserCommandService;
import org.hankki.hankkiserver.api.user.service.UserQueryService;
import org.hankki.hankkiserver.api.user.service.command.UserUniversityPostCommand;
import org.hankki.hankkiserver.api.user.service.response.UserFavoritesGetResponse;
import org.hankki.hankkiserver.api.user.service.response.UserHeartedStoreListResponse;
import org.hankki.hankkiserver.api.user.service.response.UserNicknameResponse;
import org.hankki.hankkiserver.api.user.service.response.UserStoresReportedGetResponse;
import org.hankki.hankkiserver.api.user.service.response.UserUniversityFindResponse;
import org.hankki.hankkiserver.api.common.annotation.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/users/me/university")
    public HankkiResponse<Void> postUserUniversity(@UserId final Long userId,
                                                   @Valid @RequestBody final UserUniversityPostRequest request) {
        userCommandService.saveUserUniversity(new UserUniversityPostCommand(userId, request));
        return HankkiResponse.success(CommonSuccessCode.CREATED);
    }

    @GetMapping("/users/me/university")
    public HankkiResponse<UserUniversityFindResponse> findUserUniversity(@UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, userQueryService.findUserUniversity(userId));
    }

    @GetMapping("/users/me")
    public HankkiResponse<UserNicknameResponse> getUserNickname(@UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, userQueryService.getUserNickname(userId));
    }

    @GetMapping("/users/me/favorites")
    public HankkiResponse<UserFavoritesGetResponse> findUserFavorites(@UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, userQueryService.findUserFavorites(userId));
    }

    @GetMapping("/users/me/stores/hearts")
    public HankkiResponse<UserHeartedStoreListResponse> getUserHeartedStores(@UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, userQueryService.findUserHeartedStoresView(userId));
    }

    @GetMapping("/users/me/stores/reports")
    public HankkiResponse<UserStoresReportedGetResponse> findUserStoreReported(@UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, userQueryService.findUserStoreReported(userId));
    }
}
