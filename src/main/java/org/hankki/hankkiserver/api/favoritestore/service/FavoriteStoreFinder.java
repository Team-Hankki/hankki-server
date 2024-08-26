package org.hankki.hankkiserver.api.favoritestore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.FavoriteStoreErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.favoritestore.repository.FavoriteStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteStoreFinder {

  private final FavoriteStoreRepository favoriteStoreRepository;

  public int countByFavorite(final Favorite favorite) {
    return favoriteStoreRepository.countByFavorite(favorite);
  }

  public FavoriteStore findByFavoriteIdAndStoreId(final Long favoriteId, final Long storeId) {
    return favoriteStoreRepository.findByFavoriteIdAndStoreId(favoriteId, storeId)
        .orElseThrow(() -> new NotFoundException(FavoriteStoreErrorCode.FAVORITE_STORE_NOT_FOUND));
  }

  public boolean isExist(final Long favoriteId, final Long storeId) {
    return favoriteStoreRepository.isExist(favoriteId, storeId);
  }
}