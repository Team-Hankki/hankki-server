package org.hankki.hankkiserver.api.store.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.store.controller.request.StoreDuplicateValidationRequest;
import org.hankki.hankkiserver.api.store.service.HeartCommandService;
import org.hankki.hankkiserver.api.store.service.StoreQueryService;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
import org.hankki.hankkiserver.api.store.service.command.StoreValidationCommand;
import org.hankki.hankkiserver.api.store.service.response.*;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.hankki.hankkiserver.api.store.service.command.HeartDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.HeartPostCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StoreController {

    private final StoreQueryService storeQueryService;
    private final HeartCommandService heartCommandService;

    @GetMapping("/stores/{storeId}")
    public HankkiResponse<StoreGetResponse> getStore(@PathVariable final Long storeId,
                                                     @UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoreInformation(storeId, userId));
    }

    @GetMapping("/stores/{id}/thumbnail")
    public HankkiResponse<StoreThumbnailResponse> getStoreThumbnail(@PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoreThumbnail(id));
    }

    @GetMapping("/stores/categories")
    public HankkiResponse<CategoriesResponse> getCategories() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getCategories());
    }

    @GetMapping("/stores/sort-options")
    public HankkiResponse<SortOptionsResponse> getSortOptions() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getSortOptions());
    }

    @GetMapping("/stores/price-categories")
    public HankkiResponse<PriceCategoriesResponse> getPrices() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getPriceCategories());
    }

    @PostMapping("/stores/{id}/hearts")
    public HankkiResponse<HeartCreateResponse> createHeartStore(@UserId final Long userId, @PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.CREATED, heartCommandService.createHeart(HeartPostCommand.of(userId, id)));
    }

    @DeleteMapping("/stores/{id}/hearts")
    public HankkiResponse<HeartDeleteResponse> deleteHeartStore(@UserId final Long userId, @PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.OK, heartCommandService.deleteHeart(HeartDeleteCommand.of(userId, id)));
    }

    @PostMapping("/stores/validate")
    public HankkiResponse<Void> validateDuplicatedStore(@RequestBody final StoreDuplicateValidationRequest request) {
        storeQueryService.validateDuplicatedStore(StoreValidationCommand.of(request));
        return HankkiResponse.success(CommonSuccessCode.OK);
    }
}
