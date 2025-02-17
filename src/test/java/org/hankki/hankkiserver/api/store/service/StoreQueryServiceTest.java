package org.hankki.hankkiserver.api.store.service;

import org.assertj.core.api.Assertions;
import org.hankki.hankkiserver.ServiceSliceTest;
import org.hankki.hankkiserver.api.store.parameter.PriceCategory;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.api.store.service.response.StorePageResponse;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.hankki.hankkiserver.domain.store.repository.StoreRepository;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.university.repository.UniversityRepository;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.hankki.hankkiserver.fixture.StoreFixture;
import org.hankki.hankkiserver.fixture.UniversityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class StoreQueryServiceTest extends ServiceSliceTest {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    StoreQueryService storeQueryService;
    @Autowired
    UniversityStoreRepository universityStoreRepository;
    @Autowired
    UniversityRepository universityRepository;

    @Nested
    @DisplayName("대학 필터 없이 조회시")
    class GetStoreWithoutUniversityFilter {

        @Nested
        @DisplayName("가격순 정렬")
        class SortByPrice {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(10);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6900);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name9");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(10L, 6900, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(20);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(7900);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name19");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(20L, 7900, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor()).isNull();

            }

            @Test
            @DisplayName("가격이 모두 동일할 때, 커서가 없다면 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithoutCursorWhenAllPriceSame() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6000);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");

            }

            @Test
            @DisplayName("가격이 모두 동일하다면, 커서에 따라 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithCursorWhenAllPriceSame() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(11L, 6000, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6000);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
                Assertions.assertThat(storesV2.hasNext()).isFalse();
            }
        }

        @Nested
        @DisplayName("좋아요순 정렬")
        class SortByRecommended {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(10);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(91);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name9");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(10L, null, 91));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(20);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(81);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name19");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(20L, null, 81));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor()).isNull();

            }

            @Test
            @DisplayName("좋아요가 모두 동일할 때, 커서가 없다면 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithoutCursorWhenAllHeartCountSame() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(100);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");

            }

            @Test
            @DisplayName("좋아요가 모두 동일할 때, 커서에 따라 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithCursorWhenAllHeartCountSame() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(11L,null, 100));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(100);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
                Assertions.assertThat(storesV2.hasNext()).isFalse();
            }
        }

        @Nested
        @DisplayName("최신순 정렬")
        class SortByLatest {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(11L, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i*100, 100 - i));
                }
                for (int i = 0; i < 20; i++) {
                    storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(1L, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor()).isNull();
            }
        }

    }

    @Nested
    @DisplayName("대학 필터 포함하여 조회시")
    class GetStoreWithUniversityFilter {

        @Nested
        @DisplayName("가격순 정렬")
        class SortByPrice {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(1L, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(10);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6900);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name9");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(1L, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(10L, 6900, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(20);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(7900);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name19");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000 + i * 100, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(1L, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(20L, 7900, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isNull();
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isNull();
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isNull();
            }

            @Test
            @DisplayName("가격이 모두 동일할 때, 커서가 없다면 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithoutCursorWhenAllPriceSame() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(1L, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6000);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");

            }

            @Test
            @DisplayName("가격이 모두 동일하다면, 커서에 따라 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithCursorWhenAllPriceSame() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LOWESTPRICE, new CustomCursor(11L, 6000, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isEqualTo(6000);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
                Assertions.assertThat(storesV2.hasNext()).isFalse();
            }
        }

        @Nested
        @DisplayName("좋아요순 정렬")
        class SortByRecommended {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(10);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(91);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name9");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(10L, null, 91));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(20);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(81);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name19");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(20L, null, 81));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isNull();
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isNull();
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isNull();
            }

            @Test
            @DisplayName("좋아요가 모두 동일할 때, 커서가 없다면 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithoutCursorWhenAllHeartCountSame() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(100);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");

            }

            @Test
            @DisplayName("좋아요가 모두 동일할 때, 커서에 따라 식별자 내림차순으로 정렬한다.")
            void getStoreV2WithCursorWhenAllHeartCountSame() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.RECOMMENDED, new CustomCursor(11L,null, 100));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isEqualTo(100);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
                Assertions.assertThat(storesV2.hasNext()).isFalse();
            }
        }

        @Nested
        @DisplayName("최신순 정렬")
        class SortByLatest {

            @Test
            @DisplayName("커서가 없다면 첫 페이지 결과를 반환한다.")
            void getStoreV2WithoutCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(null, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isTrue();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(11);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name10");
            }

            @Test
            @DisplayName("커서에 따라 결과를 반환한다.")
            void getStoreV2WithCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(11L, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(10);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isEqualTo(1);
                Assertions.assertThat(storesV2.stores().get(9).name()).isEqualTo("name0");
            }

            @Test
            @DisplayName("다음 페이지가 없는 요청으로 받은 커서로 요청시 빈 페이지를 반환한다.")
            void getStoreV2WithLastCursor() {
                // given
                University university = universityRepository.save(UniversityFixture.create());
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.KOREAN, 6000, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                for (int i = 0; i < 20; i++) {
                    Store store = storeRepository.save(StoreFixture.create("name" + i, StoreCategory.JAPANESE, 6000 + i, 100 - i));
                    universityStoreRepository.save(UniversityStore.create(store, university));
                }
                // when
                StorePageResponse storesV2 = storeQueryService.getStoresV2(null, StoreCategory.KOREAN, PriceCategory.K8, SortOption.LATEST, new CustomCursor(1L, null, null));
                // then
                Assertions.assertThat(storesV2.stores()).hasSize(0);
                Assertions.assertThat(storesV2.hasNext()).isFalse();
                Assertions.assertThat(storesV2.cursor().nextId()).isNull();
                Assertions.assertThat(storesV2.cursor().nextHeartCount()).isNull();
                Assertions.assertThat(storesV2.cursor().nextLowestPrice()).isNull();
            }
        }

    }
}