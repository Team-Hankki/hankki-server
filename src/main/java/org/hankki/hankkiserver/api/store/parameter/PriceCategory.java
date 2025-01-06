package org.hankki.hankkiserver.api.store.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriceCategory {

    ALL("전체", 0, 0),
    K6("6000원 이하", 6000, 0),
    K8("6000~8000원", 8000, 6000);

    private final String name;
    private final int maxPrice;
    private final int minPrice;

}
