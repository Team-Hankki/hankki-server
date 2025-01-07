package org.hankki.hankkiserver.api.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityStoreFinder {

    private final UniversityStoreRepository universityStoreRepository;

    public List<UniversityStore> findAllWithStoreByDynamicQueryWithPaging(final Long universityId, final StoreCategory storeCategory, final PriceCategory priceCategory, final SortOption sortOption) {
        return universityStoreRepository.findAllWithStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(storeCategory, priceCategory, universityId, sortOption);
    }

    protected boolean existsByUniversityIdAndStore(final Long universityId, final Store store) {
        return universityStoreRepository.existsByUniversityIdAndStore(universityId, store);
    }
}
