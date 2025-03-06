package org.hankki.hankkiserver.api.common;

import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreCategoryConverter implements Converter<String, StoreCategory> {


    @Override
    public StoreCategory convert(String source) {
        try {
            return StoreCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(StoreErrorCode.INVALID_STORE_CATEGORY);
        }
    }
}
