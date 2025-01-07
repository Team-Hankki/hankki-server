package org.hankki.hankkiserver.api.store.service;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreFinder {

    private final StoreRepository storeRepository;

    public Store findByIdWhereDeletedIsFalse(final Long id) {
        return storeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(StoreErrorCode.STORE_NOT_FOUND));
    }

    protected Store findByIdWithHeartAndIsDeletedFalse(final Long id) {
        return storeRepository.findByIdWithHeartAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(StoreErrorCode.STORE_NOT_FOUND));
    }

    protected Optional<Store> findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(final double latitude, final double longitude, final String name) {
        return storeRepository.findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(latitude, longitude, name);
    }

    protected boolean existsByLatitudeAndLongitudeAndNameAndIsDeleted(final double latitude, final double longitude, final String name, final boolean isDeleted) {
        return storeRepository.existsByPoint_LatitudeAndPoint_LongitudeAndNameAndIsDeleted(latitude, longitude, name, isDeleted);
    }

    public List<Store> findAllWithUniversityStoreByDynamicQuery(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption) {
        return storeRepository.findStoreWithUniversityStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(storeCategory, priceCategory, universityId, sortOption);
    }

    public List<Store> findAllByFavoriteStoresAndDeletedIsFalseOrderByFavoriteStoreId(final List<FavoriteStore> favoriteStores) {
        return storeRepository.findAllByFavoriteStoresAndIsDeletedIsFalseOrderByFavoriteStoreId(favoriteStores);
    }

    public List<Store> findAllByDynamicQueryWithPaging(StoreCategory storeCategory, PriceCategory priceCategory, SortOption sortOption) {
        return null;
    }
}
