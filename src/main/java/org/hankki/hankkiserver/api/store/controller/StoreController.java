package org.hankki.hankkiserver.api.store.controller;

import jakarta.validation.Valid;
import org.hankki.hankkiserver.api.common.annotation.UserId;
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
import org.hankki.hankkiserver.api.store.service.command.StoreDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
import org.hankki.hankkiserver.api.store.service.command.StoreValidationCommand;
import org.hankki.hankkiserver.api.store.service.response.CategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.api.store.service.response.HeartCreateResponse;
import org.hankki.hankkiserver.api.store.service.response.HeartDeleteResponse;
import org.hankki.hankkiserver.api.store.service.response.PriceCategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.SortOptionsResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreDuplicateValidationResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreGetResponse;
import org.hankki.hankkiserver.api.store.service.response.StorePageResponse;
import org.hankki.hankkiserver.api.store.service.response.StorePinsResponse;
import org.hankki.hankkiserver.api.store.service.response.StorePostResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreThumbnailResponse;
import org.hankki.hankkiserver.api.store.service.response.StoresResponse;
import org.hankki.hankkiserver.api.store.support.CustomCursorValidation;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final HeartCommandService heartCommandService;

    @GetMapping("/v1/stores/{storeId}")
    public HankkiResponse<StoreGetResponse> getStore(@PathVariable final Long storeId, @UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoreInformation(storeId, userId));
    }

    @GetMapping("/v1/stores/pins")
    public HankkiResponse<StorePinsResponse> getStorePins(@RequestParam(required = false) final Long universityId,
                                                          @RequestParam(required = false) final StoreCategory storeCategory,
                                                          @RequestParam(required = false) final PriceCategory priceCategory,
                                                          @RequestParam(required = false) final SortOption sortOption) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStorePins(universityId, storeCategory, priceCategory, sortOption));
    }

    @GetMapping("/v1/stores")
    public HankkiResponse<StoresResponse> getStores(@RequestParam(required = false) final Long universityId,
                                                          @RequestParam(required = false) final StoreCategory storeCategory,
                                                          @RequestParam(required = false) final PriceCategory priceCategory,
                                                          @RequestParam(required = false) final SortOption sortOption) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStores(universityId, storeCategory, priceCategory, sortOption));
    }

    @GetMapping("/v2/stores")
    public HankkiResponse<StorePageResponse> getStores(@RequestParam(required = false) final Long universityId,
                                                    @RequestParam(required = false) final StoreCategory storeCategory,
                                                    @RequestParam(required = false) final PriceCategory priceCategory,
                                                    @RequestParam(required = false) final SortOption sortOption,
                                                       @Validated @CustomCursorValidation CustomCursor cursor) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoresV2(universityId, storeCategory, priceCategory, sortOption, cursor));
    }

    @GetMapping("/v1/stores/{id}/thumbnail")
    public HankkiResponse<StoreThumbnailResponse> getStoreThumbnail(@PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getStoreThumbnail(id));
    }

    @GetMapping("/v1/stores/categories")
    public HankkiResponse<CategoriesResponse> getCategoriesV1() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getCategoriesV1());
    }

    @GetMapping("/v2/stores/categories")
    public HankkiResponse<CategoriesResponse> getCategoriesV2() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getCategoriesV2());
    }

    @GetMapping("/v1/stores/sort-options")
    public HankkiResponse<SortOptionsResponse> getSortOptions() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getSortOptions());
    }

    @GetMapping("/v1/stores/price-categories")
    public HankkiResponse<PriceCategoriesResponse> getPrices() {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.getPriceCategories());
    }

    @PostMapping("/v1/stores/{id}/hearts")
    public HankkiResponse<HeartCreateResponse> createHeartStore(@UserId final Long userId, @PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.CREATED, heartCommandService.createHeart(HeartPostCommand.of(userId, id)));
    }

    @DeleteMapping("/v1/stores/{id}/hearts")
    public HankkiResponse<HeartDeleteResponse> deleteHeartStore(@UserId final Long userId, @PathVariable final Long id) {
        return HankkiResponse.success(CommonSuccessCode.OK, heartCommandService.deleteHeart(HeartDeleteCommand.of(userId, id)));
    }

    @PostMapping("/v1/stores/validate")
    public HankkiResponse<StoreDuplicateValidationResponse> validateDuplicatedStore(@RequestBody @Valid final StoreDuplicateValidationRequest request) {
        return HankkiResponse.success(CommonSuccessCode.OK, storeQueryService.validateDuplicatedStore(StoreValidationCommand.of(request.universityId(), request.latitude(), request.longitude(), request.storeName())));
    }

    @PostMapping("/v1/stores")
    public HankkiResponse<StorePostResponse> createStore(@RequestPart(required = false) final MultipartFile image,
                                                           @Valid @RequestPart final StorePostRequest request,
                                                         @UserId final Long userId) {
        return HankkiResponse.success(CommonSuccessCode.CREATED, storeCommandService.createStore(StorePostCommand.of(image, request, userId)));
    }

    @DeleteMapping("/v1/stores/{id}")
    public HankkiResponse<Void> deleteStore(@PathVariable final Long id, @UserId final Long userId) {
        storeCommandService.deleteStore(StoreDeleteCommand.of(id, userId));
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }
}
