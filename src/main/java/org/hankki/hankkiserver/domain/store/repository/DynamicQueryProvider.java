package org.hankki.hankkiserver.domain.store.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;
import static org.hankki.hankkiserver.domain.universitystore.model.QUniversityStore.universityStore;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.stereotype.Component;

@Component
public class DynamicQueryProvider {

    public BooleanExpression hasUniversity(Long university) {
        if (university == null) {
            return null;
        }
        return store.universityStores.any().university.id.eq(university);
    }

    public BooleanExpression eqCategory(StoreCategory category) {
        if (category == null) {
            return null;
        }
        return store.category.eq(category);
    }

    public BooleanExpression evaluatePriceCategory(PriceCategory priceCategory) {
        if (priceCategory == null) {
            return null;
        }
        return store.lowestPrice.goe(priceCategory.getMinPrice())
                .and(store.lowestPrice.loe(priceCategory.getMaxPrice()));
    }

    public BooleanExpression eqUniversity(Long university) {
        if (university == null) {
            return null;
        }
        return universityStore.university.id.eq(university);
    }

}
