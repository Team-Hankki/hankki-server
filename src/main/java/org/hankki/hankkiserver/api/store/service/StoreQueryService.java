package org.hankki.hankkiserver.api.store.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.api.menu.service.MenuFinder;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.command.StoreValidationCommand;
import org.hankki.hankkiserver.api.store.service.response.CategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.CategoryResponse;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.api.store.service.response.MenuResponse;
import org.hankki.hankkiserver.api.store.service.response.PinResponse;
import org.hankki.hankkiserver.api.store.service.response.PriceCategoriesResponse;
import org.hankki.hankkiserver.api.store.service.response.PriceCategoryResponse;
import org.hankki.hankkiserver.api.store.service.response.SortOptionResponse;
import org.hankki.hankkiserver.api.store.service.response.SortOptionsResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreDuplicateValidationResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreGetResponse;
import org.hankki.hankkiserver.api.store.service.response.StorePageResponse;
import org.hankki.hankkiserver.api.store.service.response.StorePinsResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreThumbnailResponse;
import org.hankki.hankkiserver.api.store.service.response.StoresResponse;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.store.model.StoreImage;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreQueryService {

    private static final int PAGE_SIZE = 10;

    private final StoreFinder storeFinder;
    private final UniversityStoreFinder universityStoreFinder;
    private final MenuFinder menuFinder;

    @Transactional(readOnly = true)
    public StoreThumbnailResponse getStoreThumbnail(final Long id) {
        return StoreThumbnailResponse.of(storeFinder.findByIdWhereDeletedIsFalse(id));
    }

    @Transactional(readOnly = true)
    public StoreGetResponse getStoreInformation(final Long storeId, final Long userId) {

        Store store = storeFinder.findByIdWithHeartAndIsDeletedFalse(storeId);

        return StoreGetResponse.of(store,
                isLiked(userId, store),
                getImageUrlsFromStore(store),
                getMenus(store));
    }

    @Transactional(readOnly = true)
    public StorePinsResponse getStorePins(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption) {
        return StorePinsResponse.of(storeFinder.findAllWithUniversityStoreByDynamicQuery(universityId, storeCategory, priceCategory, sortOption)
                .stream().map(PinResponse::of).toList());
    }

    @Transactional(readOnly = true)
    public StoresResponse getStores(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption) {
        return StoresResponse.of(storeFinder.findAllWithUniversityStoreByDynamicQuery(universityId, storeCategory, priceCategory, sortOption)
                .stream().map(StoreResponse::of).toList());
    }

    @Transactional(readOnly = true)
    public StorePageResponse getStoresV2(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption, final CustomCursor cursor) {
        if (universityId == null) {
            List<Store> stores = storeFinder.findAllByDynamicQueryWithPaging(storeCategory, priceCategory, sortOption, cursor);
            List<StoreResponse> storeResponses = stores.stream().map(StoreResponse::of).toList();
            Store lastStore = stores.get(stores.size() - 1);
            return StorePageResponse.of(storeResponses, isNextPageAvailable(stores), CustomCursor.createNextCursor(sortOption, lastStore));
        }

        List<UniversityStore> universityStores = universityStoreFinder.findAllWithStoreByDynamicQueryWithPaging(
                universityId, storeCategory, priceCategory, sortOption, cursor);
        List<StoreResponse> storeResponses = universityStores.stream().map(StoreResponse::of).toList();
        Store lastStore = universityStores.get(universityStores.size() - 1).getStore();
        return StorePageResponse.of(storeResponses, isNextPageAvailable(universityStores), CustomCursor.createNextCursor(sortOption, lastStore));
    }

    private <T>boolean isNextPageAvailable(List<T> elements) {
        return elements.size() == PAGE_SIZE;
    }

    public CategoriesResponse getCategoriesV1() {
        return new CategoriesResponse(Arrays.stream(StoreCategory.values())
                .filter(category -> !category.equals(StoreCategory.ALL))
                .map(CategoryResponse::of)
                .toList());
    }

    public CategoriesResponse getCategoriesV2() {
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

    @Transactional(readOnly = true)
    public StoreDuplicateValidationResponse validateDuplicatedStore(final StoreValidationCommand command) {
        return createStoreDuplicateValidationResponse(storeFinder.findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(command.latitude(), command.longitude(), command.name()), command.universityId());
    }

    private List<MenuResponse> getMenus(final Store store) {
        return menuFinder.findAllByStore(store).stream().map(MenuResponse::of).toList();
    }

    private boolean isLiked(final Long userId, final Store store) {
        return store.getHearts().stream().anyMatch(heart -> hasSameUserId(userId, heart));
    }

    private boolean hasSameUserId(final Long id, final Heart heart) {
        return heart.getUser().getId().equals(id);
    }

    private StoreDuplicateValidationResponse createStoreDuplicateValidationResponse(Optional<Store> store, Long universityId) {
        return store.map(s -> StoreDuplicateValidationResponse.of(s.getId(), universityStoreFinder.existsByUniversityIdAndStore(universityId, s)))
                .orElseGet(() -> StoreDuplicateValidationResponse.of(null, false));
    }
}
