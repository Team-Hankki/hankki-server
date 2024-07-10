package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.api.store.parameter.PriceCategory;

public record PriceCategoryResponse(String name, String tag) {
    public static PriceCategoryResponse of(PriceCategory priceCategory) {
        return new PriceCategoryResponse(priceCategory.getName(), priceCategory.toString());
    }
}
