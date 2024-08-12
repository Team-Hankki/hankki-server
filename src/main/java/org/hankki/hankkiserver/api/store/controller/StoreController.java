package org.hankki.hankkiserver.api.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.store.controller.request.StoreDuplicateValidationRequest;
import org.hankki.hankkiserver.api.store.controller.request.StorePostRequest;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.HeartCommandService;
import org.hankki.hankkiserver.api.store.service.StoreCommandService;
import org.hankki.hankkiserver.api.store.service.StoreQueryService;
import org.hankki.hankkiserver.api.store.service.command.HeartDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.HeartPostCommand;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
import org.hankki.hankkiserver.api.store.service.command.StoreValidationCommand;
import org.hankki.hankkiserver.api.store.service.response.*;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StoreController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final HeartCommandService heartCommandService;

    @GetMapping("/stores/{storeId}")
    public HankkiResponse<StoreGetResponse> getStore(@PathVariable final Long storeId, @UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoreInformation(storeId, userId));
    }

    @GetMapping("/stores/pins")
    public HankkiResponse<StorePinsResponse> getStorePins(@RequestParam(required = false) final Long universityId,
                                                          @RequestParam(required = false) final StoreCategory storeCategory,
                                                          @RequestParam(required = false) final PriceCategory priceCategory,
                                                          @RequestParam(required = false) final SortOption sortOption) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStorePins(universityId, storeCategory, priceCategory, sortOption));
    }

    @GetMapping("/stores")
    public HankkiResponse<StoresResponse> getStores(@RequestParam(required = false) final Long universityId,
                                                          @RequestParam(required = false) final StoreCategory storeCategory,
                                                          @RequestParam(required = false) final PriceCategory priceCategory,
                                                          @RequestParam(required = false) final SortOption sortOption) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStores(universityId, storeCategory, priceCategory, sortOption));
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

    @PostMapping("/stores")
    public HankkiResponse<StorePostResponse> createStore(@RequestPart(required = false) final MultipartFile image,
                                                           @Valid @RequestPart final StorePostRequest request,
                                                         @UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.CREATED, storeCommandService.createStore(StorePostCommand.of(image, request, userId)));
    }

    @DeleteMapping("/stores/{id}")
    public HankkiResponse<Void> deleteStore(@PathVariable final Long id) {
        storeCommandService.deleteStore(id);
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }
}
