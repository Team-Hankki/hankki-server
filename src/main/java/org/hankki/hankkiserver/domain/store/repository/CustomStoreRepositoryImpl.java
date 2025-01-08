package org.hankki.hankkiserver.domain.store.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

import com.querydsl.core.types.dsl.BooleanExpression;
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
                .where(eqCategory(category),eqUniversity(universityId), evaluatePriceCategory(priceCategory))
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
                .where(eqCategory(category), evaluatePriceCategory(priceCategory)) // 필터
                .where(store.isDeleted.isFalse())
                .orderBy(orderSpecifierProvider.createOrderSpecifierForPaging(sortOptions))//정렬
                .limit(PAGE_SIZE)
                .fetch();
    }

    private BooleanExpression eqUniversity(Long university) {
        if (university == null) {
            return null;
        }
        return store.universityStores.any().university.id.eq(university);
    }

    private BooleanExpression eqCategory(StoreCategory category) {
        if (category == null) {
            return null;
        }
        return store.category.eq(category);
    }

    private BooleanExpression evaluatePriceCategory(PriceCategory priceCategory) {
        if (priceCategory == null) {
            return null;
        }
        return store.lowestPrice.goe(priceCategory.getMinPrice())
                .and(store.lowestPrice.loe(priceCategory.getMaxPrice()));
    }
}
