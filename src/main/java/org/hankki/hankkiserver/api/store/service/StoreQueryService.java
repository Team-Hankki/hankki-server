package org.hankki.hankkiserver.api.store.service;


import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.MenuFinder;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.*;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.store.model.StoreImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreFinder storeFinder;
    private final MenuFinder menuFinder;

    @Transactional(readOnly = true)
    public StoreThumbnailResponse getStoreThumbnail(final Long id) {
        return StoreThumbnailResponse.of(storeFinder.findByIdWhereDeletedIsFalse(id));
    }

    @Transactional(readOnly = true)
    public StoreGetResponse getStoreInformation(final Long id) {

        Store store = storeFinder.findByIdWithHeartAndIsDeletedFalse(id);

        return StoreGetResponse.of(store,
                isLiked(id, store),
                getImageUrlsFromStore(store),
                getMenus(store));
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

    private List<String> getImageUrlsFromStore(final Store store) {
        return store.getImages().stream()
                .map(StoreImage::getImageUrl)
                .toList();
    }

    private List<MenuResponse> getMenus(final Store store) {
        return menuFinder.findAllByStore(store).stream().map(MenuResponse::of).toList();
    }

    private boolean isLiked(final Long id, final Store store) {
        return store.getHearts().stream().anyMatch(heart -> heart.getUser().getId().equals(id));
    }
}
