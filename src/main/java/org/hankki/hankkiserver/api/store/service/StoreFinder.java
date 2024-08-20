package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
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

    protected Optional<Store> findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(final double latitude, final double longitude, String name) {
        return storeRepository.findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(latitude, longitude, name);
    }

    protected boolean existsByLatitudeAndLongitude(final double latitude, final double longitude) {
        return storeRepository.existsByPoint_LatitudeAndPoint_Longitude(latitude, longitude);
    }

    public List<Store> findAllDynamicQuery(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption) {
        return storeRepository.findStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptions(storeCategory, priceCategory, universityId, sortOption);
    }

    public List<Store> findAllByIdsWhereDeletedIsFalseOrderByCreatedAtDes(final List<Long> ids) {
        return storeRepository.findAllByIdsAndIsDeletedIsFalseOrderByCreatedAtDesc(ids);
    }
}
