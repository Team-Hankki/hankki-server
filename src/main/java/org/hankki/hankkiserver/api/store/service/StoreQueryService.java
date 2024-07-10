package org.hankki.hankkiserver.api.store.service;


import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.*;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StoreQueryService {
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
