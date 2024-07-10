package org.hankki.hankkiserver.api.store.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOption {
    LATEST("최신순"),
    RECOMMENDED("추천순");

    private final String name;
}
