package org.hankki.hankkiserver.api.store.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriceCategory {

    K6("6000원 이하", "6000"),
    k8("6000~8000", "8000");

    private final String name;
    private final String maxPrice;

}
