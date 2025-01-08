package org.hankki.hankkiserver.api.common;

import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortOptionConverter implements Converter<String, SortOption> {

    @Override
    public SortOption convert(String source) {
        return SortOption.valueOf(source.toUpperCase());
    }
}
