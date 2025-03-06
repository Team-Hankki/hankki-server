package org.hankki.hankkiserver.domain.universitystore.repository;

import java.util.List;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;

public interface CustomUniversityStoreRepository {
    List<UniversityStore> findAllWithStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(
            StoreCategory category, PriceCategory priceCategory, Long universityId, SortOption sortOptions, CustomCursor cursor, int limitSize);
}
