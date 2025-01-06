package org.hankki.hankkiserver.api.common;

import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
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
        return PriceCategory.valueOf(source.toUpperCase());
    }
}
