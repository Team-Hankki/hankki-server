package org.hankki.hankkiserver.domain.store.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.springframework.stereotype.Component;

@Component
public class CursorProvider {
    public BooleanExpression createCursorCondition(CustomCursor cursor, SortOption sortOptions) {
        if (sortOptions == null) {
            return createCursorWhenSortByLatest(cursor);
        }
        return switch (sortOptions) {
            case LATEST -> createCursorWhenSortByLatest(cursor);
            case LOWESTPRICE -> createCursorWhenSortByLowestPrice(cursor);
            case RECOMMENDED -> createCursorWhenSortByRecommended(cursor);
        };
    }

    private  BooleanExpression createCursorWhenSortByRecommended(CustomCursor cursor) {
        if (cursor.nextId() == null || cursor.nextHeartCount() == null) {
            return null;
        }
        return store.heartCount.lt(cursor.nextHeartCount())
                .or(store.id.lt(cursor.nextId()).and(store.heartCount.eq(cursor.nextHeartCount())));
    }

    private BooleanExpression createCursorWhenSortByLowestPrice(CustomCursor cursor) {
        if (cursor.nextId() == null || cursor.nextLowestPrice() == null) {
            return null;
        }
        return store.lowestPrice.gt(cursor.nextLowestPrice())
                .or(store.id.lt(cursor.nextId()).and(store.lowestPrice.eq(cursor.nextLowestPrice())));
    }

    private  BooleanExpression createCursorWhenSortByLatest(CustomCursor cursor) {
        if (cursor.nextId() == null) {
            return null;
        }
        return store.id.lt(cursor.nextId());
    }
}
