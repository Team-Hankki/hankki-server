package org.hankki.hankkiserver.domain.store.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

@RequiredArgsConstructor
public class CustomStoreRepositoryImpl implements CustomStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> findStoreWithUniversityStoreByCategoryAndLowestPriceAndUniversityIdAndIsDeletedFalseOrderBySortOptionsWithPaging(
            StoreCategory category,
            PriceCategory priceCategory,
            Long universityId,
            SortOption sortOptions) {

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(sortOptions);
        //이거 쿼리 문제점 대학 필터 없을 때 조인 필요없는데 조인 돼서 나감
        return jpaQueryFactory
                .select(store)
                .from(store)
                .join(store.universityStores, universityStore).fetchJoin()
                .where(eqCategory(category),eqUniversity(universityId), evaluatePriceCategory(priceCategory))
                .where(store.isDeleted.isFalse())
                .orderBy(orderSpecifiers)
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

    private OrderSpecifier[] createOrderSpecifier(SortOption sortOption) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (Objects.isNull(sortOption)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.createdAt));
        }
        else if (SortOption.LATEST == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.createdAt));
        }
        else if (SortOption.LOWESTPRICE == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, store.lowestPrice));
        }
        else if (SortOption.RECOMMENDED == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.heartCount));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
