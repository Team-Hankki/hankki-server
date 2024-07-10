package org.hankki.hankkiserver.api.store.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.store.service.StoreQueryService;
import org.hankki.hankkiserver.api.store.service.response.CategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.PriceCategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.SortOptionsResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreThumbnailResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.hankki.hankkiserver.api.store.service.StoreCommandService;
import org.hankki.hankkiserver.auth.UserId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StoreController {

    private final StoreQueryService storeQueryService;
    private final StoreCommandService storeCommandService;

    @GetMapping("/stores/{id}/thumbnail")
    public HankkiResponse<StoreThumbnailResponse> getStoreThumbnail(@PathVariable Long id) {
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

    @PostMapping("/stores/{storeId}/heart")
    public HankkiResponse<Void> heartStore(@UserId Long userId, @PathVariable Long storeId) {
        storeCommandService.createHeart(userId, storeId);
        return HankkiResponse.success(CommonSuccessCode.CREATED);
    }
}
