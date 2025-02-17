package org.hankki.hankkiserver.api.common;

import org.assertj.core.api.Assertions;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceCategoryConverterTest {

    @Test
    @DisplayName("가격 카테고리로 전체를 넘겨주면 아무것도 매핑하지 않는다")
    void convertCategoryAll() {
        PriceCategoryConverter priceCategoryConverter = new PriceCategoryConverter();
        String priceCategory = "ALL";
        PriceCategory result = priceCategoryConverter.convert(priceCategory);
        Assertions.assertThat(result).isNull();
    }
}