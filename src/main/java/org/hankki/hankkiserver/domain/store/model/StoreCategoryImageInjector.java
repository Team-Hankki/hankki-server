package org.hankki.hankkiserver.domain.store.model;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StoreCategoryImageInjector {

  @Value("${store.category-image.boonsick}")
  private String boonsickUrl;

  @Value("${store.category-image.chinese}")
  private String chineseUrl;

  @Value("${store.category-image.fastfood}")
  private String fastfoodUrl;

  @Value("${store.category-image.world}")
  private String worldUrl;

  @Value("${store.category-image.korean}")
  private String koreanUrl;

  @Value("${store.category-image.salad}")
  private String saladUrl;

  @Value("${store.category-image.conveniencefood}")
  private String conveniencefoodUrl;

  @Value("${store.category-image.japanese}")
  private String japaneseUrl;

  @Value("${store.category-image.western}")
  private String western;

  @PostConstruct
  public void init() {
    StoreCategory.BUNSIK.setUrl(boonsickUrl);
    StoreCategory.CHINESE.setUrl(chineseUrl);
    StoreCategory.FASTFOOD.setUrl(fastfoodUrl);
    StoreCategory.WORLD.setUrl(worldUrl);
    StoreCategory.KOREAN.setUrl(koreanUrl);
    StoreCategory.SALADSANDWICH.setUrl(saladUrl);
    StoreCategory.CONVENIENCEFOOD.setUrl(conveniencefoodUrl);
    StoreCategory.JAPANESE.setUrl(japaneseUrl);
    StoreCategory.WESTERN.setUrl(western);
  }
}
