package org.hankki.hankkiserver.api.favorite.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.exception.InternalServerException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteDeleter {

  private final FavoriteRepository favoriteRepository;
  public void deleteAll(List<Favorite> favorites) {

    try {
      favoriteRepository.deleteAll(favorites);
    } catch (Exception e) {
      throw new InternalServerException(BusinessErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
