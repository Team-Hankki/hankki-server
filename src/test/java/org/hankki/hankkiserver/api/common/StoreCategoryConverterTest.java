package org.hankki.hankkiserver.api.common;

import org.assertj.core.api.Assertions;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class StoreCategoryConverterTest {

    @Test
    void convertToEntityAttribute() {
        StoreCategoryConverter storeCategoryConverter = new StoreCategoryConverter();
        String storeCategory = "ㅁㅁㅁ";
        Assertions.assertThatThrownBy(() -> storeCategoryConverter.convert(storeCategory)).isInstanceOf(BadRequestException.class);
    }
}