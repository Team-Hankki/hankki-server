package org.hankki.hankkiserver.domain.store.repository;

import java.util.List;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.stereotype.Component;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

@Component
@RequiredArgsConstructor
public class CustomStoreRepositoryImpl implements CustomStoreRepository {
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
            CustomCursor cursor,
            int limitSize) {
        return jpaQueryFactory
                .select(store)
                .from(store)
                // 커서
                .where(cursorProvider.createCursorCondition(cursor, sortOptions))
                // 필터
                .where(dynamicQueryProvider.eqCategory(category),
                        dynamicQueryProvider.evaluatePriceCategory(priceCategory))
                .where(store.isDeleted.isFalse())
                // 정렬
                .orderBy(orderSpecifierProvider.createOrderSpecifierForPaging(sortOptions))
                .limit(limitSize)
                .fetch();
    }
}
