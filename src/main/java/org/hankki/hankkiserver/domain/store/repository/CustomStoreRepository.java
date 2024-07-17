package org.hankki.hankkiserver.domain.store.repository;

import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;

import java.util.List;

public interface CustomStoreRepository {
    List<Store> findStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptions(StoreCategory category, PriceCategory priceCategory, Long universityId, SortOption sortOptions);
}
