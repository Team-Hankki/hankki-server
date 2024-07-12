package org.hankki.hankkiserver.api.favoritestore.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.favoritestore.repository.FavoriteStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteStoreUpdater {

  private final FavoriteStoreRepository favoriteStoreRepository;

  public FavoriteStore save(FavoriteStore favoriteStore) {
    return favoriteStoreRepository.save(favoriteStore);
  }
}