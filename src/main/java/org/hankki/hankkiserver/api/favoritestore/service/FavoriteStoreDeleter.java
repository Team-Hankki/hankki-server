package org.hankki.hankkiserver.api.favoritestore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favoritestore.repository.FavoriteStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteStoreDeleter {

  private final FavoriteStoreRepository favoriteStoreRepository;

  public void deleteByIds(List<Long> ids) {
    favoriteStoreRepository.deleteAllById(ids);
  }
}
