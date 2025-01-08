package org.hankki.hankkiserver.api.store.service.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomCursor(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long nextId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer nextLowestPrice,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer nextHeartCount) {

    public static CustomCursorBuilder builder() {
        return new CustomCursorBuilder();
    }

    public static class CustomCursorBuilder {
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
