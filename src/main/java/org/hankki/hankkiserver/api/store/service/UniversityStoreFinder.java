package org.hankki.hankkiserver.api.store.service;

import java.util.List;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniversityStoreFinder {

    private final UniversityStoreRepository universityStoreRepository;

    public List<UniversityStore> findAllWithStoreByDynamicQueryWithPaging(final Long universityId,
                                                                          final StoreCategory storeCategory,
                                                                          final PriceCategory priceCategory,
                                                                          final SortOption sortOption,
                                                                          final CustomCursor cursor,
                                                                          final int PAGE_SIZE) {
        return universityStoreRepository.findAllWithStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(storeCategory, priceCategory, universityId, sortOption, cursor, PAGE_SIZE);
    }

    protected boolean existsByUniversityIdAndStore(final Long universityId, final Store store) {
        return universityStoreRepository.existsByUniversityIdAndStore(universityId, store);
    }
}
