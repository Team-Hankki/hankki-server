package org.hankki.hankkiserver.domain.universitystore.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.store.repository.CursorProvider;
import org.hankki.hankkiserver.domain.store.repository.DynamicQueryProvider;
import org.hankki.hankkiserver.domain.store.repository.OrderSpecifierProvider;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUniversityStoreRepositoryImpl implements CustomUniversityStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final OrderSpecifierProvider orderSpecifierProvider;
    private final CursorProvider cursorProvider;
    private final DynamicQueryProvider dynamicQueryProvider;
    @Override
    public List<UniversityStore> findAllWithStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(
            StoreCategory category, PriceCategory priceCategory, Long universityId, SortOption sortOptions, CustomCursor cursor, int PAGE_SIZE) {
        return jpaQueryFactory
                .select(universityStore)
                .from(universityStore)
                .join(universityStore.store, store).fetchJoin()
                .where(cursorProvider.createCursorCondition(cursor, sortOptions))
                .where(dynamicQueryProvider.eqUniversity(universityId),
                        dynamicQueryProvider.eqCategory(category),
                        dynamicQueryProvider.evaluatePriceCategory(priceCategory))
                .where(universityStore.store.isDeleted.isFalse())
                .orderBy(orderSpecifierProvider.createOrderSpecifierForPaging(sortOptions))
                .limit(PAGE_SIZE)
                .fetch();
    }
 }

