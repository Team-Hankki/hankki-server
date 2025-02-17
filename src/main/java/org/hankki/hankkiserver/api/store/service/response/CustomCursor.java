package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.domain.store.model.Store;
import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomCursor(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long nextId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer nextLowestPrice,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer nextHeartCount) {

    public static CustomCursor createNextCursor(SortOption sortOption, Store store) {

        if (sortOption == null) {
            return CustomCursor.builder()
                    .nextId(store.getId())
                    .build();
        }

        switch (sortOption) {
            case RECOMMENDED -> {
                return CustomCursor.builder()
                        .nextId(store.getId())
                        .nextHeartCount(store.getHeartCount())
                        .build();
            }
            case LOWESTPRICE -> {
                return CustomCursor.builder()
                        .nextId(store.getId())
                        .nextLowestPrice(store.getLowestPrice())
                        .build();
            }
            default -> {
                return CustomCursor.builder()
                        .nextId(store.getId())
                        .build();
            }
        }
    }

    private static CustomCursorBuilder builder() {
        return new CustomCursorBuilder();

    }

    private static class CustomCursorBuilder {
        private Long nextId;
        private Integer nextLowestPrice;
        private Integer nextHeartCount;

        public CustomCursorBuilder nextId(Long nextId) {
            this.nextId = nextId;
            return this;
        }

        public CustomCursorBuilder nextLowestPrice(Integer nextLowestPrice) {
            this.nextLowestPrice = nextLowestPrice;
            return this;
        }

        public CustomCursorBuilder nextHeartCount(Integer nextHeartCount) {
            this.nextHeartCount = nextHeartCount;
            return this;
        }

        public CustomCursor build() {
            return new CustomCursor(nextId, nextLowestPrice, nextHeartCount);
        }
    }
}
