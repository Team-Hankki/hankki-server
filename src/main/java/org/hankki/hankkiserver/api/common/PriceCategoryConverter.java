package org.hankki.hankkiserver.api.common;

import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceCategoryConverter implements Converter<String, PriceCategory> {

    @Override
    public PriceCategory convert(String source) {
        if (source.toUpperCase().equals(PriceCategory.ALL.toString())) {
            return null;
        }
        try {
            return PriceCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(StoreErrorCode.INVALID_PRICE_CATEGORY);
        }
    }
}
