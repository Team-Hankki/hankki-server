package org.hankki.hankkiserver.api.store.controller;

import java.util.List;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.HeartCommandService;
import org.hankki.hankkiserver.api.store.service.StoreCommandService;
import org.hankki.hankkiserver.api.store.service.StoreQueryService;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.api.store.service.response.StorePageResponse;
import org.hankki.hankkiserver.api.store.service.response.StoreResponse;
import org.hankki.hankkiserver.auth.config.SecurityConfig;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.fixture.StoreFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StoreController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class StoreControllerTest {

    @MockBean
    private StoreCommandService storeCommandService;
    @MockBean
    private StoreQueryService storeQueryService;
    @MockBean
    private HeartCommandService heartCommandService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("정상적인 파라미터로 조회시, 페이징된 결과를 반환한다.")
    void getStoresWithInvalidCursor() throws Exception {
        Store store = StoreFixture.create("name", StoreCategory.KOREAN, 3000, 6000);
        StorePageResponse storePageResponse = StorePageResponse.of(List.of(new StoreResponse(1, "imageUrl", StoreCategory.KOREAN.getName(), "name", 3000, 6000)),
                false,
                CustomCursor.createNextCursor(SortOption.LOWESTPRICE, store));

        when(storeQueryService.getStoresV2(any(), any(), any(), any(), any()))
                .thenReturn(storePageResponse);

        mockMvc.perform(get("/api/v2/stores")
                        .param("storeCategory", "KOREAN")
                        .param("priceCategory", "K8")
                        .param("sortOption", "LOWESTPRICE")
                        .param("nextId", "10")
                        .param("nextLowestPrice", "6000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.hasNext").value(false))
                .andExpect(jsonPath("$.data.stores[0].name").value(store.getName()))
                .andExpect(jsonPath("$.data.stores[0].lowestPrice").value(store.getLowestPrice()))
                .andExpect(jsonPath("$.data.stores[0].heartCount").value(store.getHeartCount()))
                .andExpect(jsonPath("$.data.stores[0].category").value(store.getCategory().getName()));

    }
    @Test
    @DisplayName("정렬 옵션에 맞지 않는 커서로 요청하면 실패한다")
    void failGetStoresWithInvalidCursor() throws Exception {
        mockMvc.perform(get("/api/v2/stores")
                        .param("storeCategory", "KOREAN")
                        .param("priceCategory", "K8")
                        .param("sortOption", "LOWESTPRICE")
                        .param("nextLowestPrice", "6000"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(StoreErrorCode.BAD_CURSOR_SET.getMessage()));

    }

    @Test
    @DisplayName("올바른 카테고리 필터값을 주지 않으면 실패한다.")
    void failGetStoresWithInvalidCategoryOption() throws Exception {
        mockMvc.perform(get("/api/v2/stores")
                        .param("storeCategory", "aa"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(StoreErrorCode.INVALID_STORE_CATEGORY.getMessage()));
    }

    @Test
    @DisplayName("올바른 가격대 필터값을 주지 않으면 실패한다.")
    void failGetStoresWithInvalidPriceOption() throws Exception {
        mockMvc.perform(get("/api/v2/stores")
                        .param("priceCategory", "aa"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(StoreErrorCode.INVALID_PRICE_CATEGORY.getMessage()));
    }

    @Test
    @DisplayName("올바른 정렬값을 주지 않으면 실패한다.")
    void failGetStoresWithInvalidSortOption() throws Exception {
        mockMvc.perform(get("/api/v2/stores")
                        .param("sortOption", "aa"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(StoreErrorCode.INVALID_SORT_OPTION.getMessage()));
    }
}
