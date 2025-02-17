package org.hankki.hankkiserver.api.common;

import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortOptionConverter implements Converter<String, SortOption> {

    @Override
    public SortOption convert(String source) {
        try {
            return SortOption.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(StoreErrorCode.INVALID_SORT_OPTION);
        }

    }
}
