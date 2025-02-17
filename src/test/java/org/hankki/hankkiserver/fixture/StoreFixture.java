package org.hankki.hankkiserver.fixture;


import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;

public abstract class StoreFixture {
    private StoreFixture() {
    }

    public static Store create(String name, StoreCategory category, int lowestPrice, int heartCount) {
        return Store.builder()
                .name(name)
                .point(new Point(1.0, 1.0))
                .address("address")
                .category(category)
                .lowestPrice(lowestPrice)
                .heartCount(heartCount)
                .isDeleted(false)
                .build();
    }
}
