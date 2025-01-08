package org.hankki.hankkiserver.domain.store.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomStoreRepositoryImpl implements CustomStoreRepository {
    private static final int PAGE_SIZE = 10;

    private final JPAQueryFactory jpaQueryFactory;
    private final OrderSpecifierProvider orderSpecifierProvider;
    private final CursorProvider cursorProvider;
    private final DynamicQueryProvider dynamicQueryProvider;

    @Override
    public List<Store> findAllWithUniversityStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptions(
            StoreCategory category,
            PriceCategory priceCategory,
            Long universityId,
            SortOption sortOptions) {
        return jpaQueryFactory
                .select(store)
                .from(store)
                .join(store.universityStores, universityStore).fetchJoin()
                .where(dynamicQueryProvider.eqCategory(category),
                        dynamicQueryProvider.hasUniversity(universityId),
                        dynamicQueryProvider.evaluatePriceCategory(priceCategory))
                .where(store.isDeleted.isFalse())
                .orderBy(orderSpecifierProvider.createOrderSpecifier(sortOptions))
                .fetch();
    }

    @Override
    public List<Store> findAllByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(
            StoreCategory category,
            PriceCategory priceCategory,
            SortOption sortOptions,
            CustomCursor cursor) {
        return jpaQueryFactory
                .select(store)
                .from(store)
                .where(cursorProvider.createCursorCondition(cursor, sortOptions))
                .where(dynamicQueryProvider.eqCategory(category),
                        dynamicQueryProvider.evaluatePriceCategory(priceCategory)) // 필터
                .where(store.isDeleted.isFalse())
                .orderBy(orderSpecifierProvider.createOrderSpecifierForPaging(sortOptions))//정렬
                .limit(PAGE_SIZE)
                .fetch();
    }
}
