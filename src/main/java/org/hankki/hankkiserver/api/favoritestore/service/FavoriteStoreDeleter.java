package org.hankki.hankkiserver.api.favoritestore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.exception.InternalServerException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.favoritestore.repository.FavoriteStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteStoreDeleter {

  private final FavoriteStoreRepository favoriteStoreRepository;

  public void deleteAllByFavorites(final List<Favorite> favorites) {
     favoriteStoreRepository.deleteAllByFavorites(favorites);
  }

  public void delete(final FavoriteStore favoriteStore) {
    favoriteStoreRepository.delete(favoriteStore);
  }
}
