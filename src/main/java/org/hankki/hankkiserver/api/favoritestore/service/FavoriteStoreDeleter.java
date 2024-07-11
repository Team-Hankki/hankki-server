package org.hankki.hankkiserver.api.favoritestore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.exception.InternalServerException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.repository.FavoriteStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteStoreDeleter {

  private final FavoriteStoreRepository favoriteStoreRepository;

  public void deleteAllByFavorites(List<Favorite> favorites) {

    try {
      favoriteStoreRepository.deleteAllByFavorites(favorites);
    } catch (Exception e) {
      throw new InternalServerException(BusinessErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
