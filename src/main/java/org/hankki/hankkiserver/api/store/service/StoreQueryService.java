package org.hankki.hankkiserver.api.store.service;


import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.*;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreFinder storeFinder;

    @Transactional(readOnly = true)//주어진 pk를 가진 식당의 정보를 조회 이때 식당의 상태가 is_deleted면 404를 나타낸다.
    public StoreThumbnailResponse getStoreThumbnail(Long id) {
        return StoreThumbnailResponse.of(storeFinder.findByIdWhereDeletedIsFalse(id));
    }

    public CategoriesResponse getCategories() {
        return new CategoriesResponse(Arrays.stream(StoreCategory.values())
                .map(CategoryResponse::of)
                .toList());
    }

    public SortOptionsResponse getSortOptions() {
        return new SortOptionsResponse(Arrays.stream(SortOption.values())
                .map(SortOptionResponse::of)
                .toList());
    }

    public PriceCategoriesResponse getPriceCategories() {
        return new PriceCategoriesResponse(Arrays.stream(PriceCategory.values())
                .map(PriceCategoryResponse::of)
                .toList());
    }
}
